#!/usr/bin/env python
import cv2
import threading
import Queue as que
import time
import numpy as np

import roslib
import sys
import rospy

import importlib
import cPickle
import genpy.message
from rospy import ROSException
import sensor_msgs.msg
import actionlib
import rostopic
import rosservice
from rosservice import ROSServiceException

from image_process import *
from slidewindow import SlideWindow
from warper import Warper
from pidcal import PidCal
from obstacle import detect_obstacle

from std_msgs.msg import String
from sensor_msgs.msg import Image
from cv_bridge import CvBridge, CvBridgeError
import time

from ackermann_msgs.msg import AckermannDriveStamped

warper = Warper()
slidewindow = SlideWindow()
pidcal = PidCal()
processor = ImageProcessor(clustering_op='theta')

q1 = que.Queue()
bridge = CvBridge()

cv_image = None
ack_publisher = None
run = False
max_speed = 0.3 if run else 0.0
car_run_speed = max_speed * 0.5
turning_coef = 1.0 if run else 0.0

op = None


def img_callback(data):
    global cv_image
    try:
        cv_image = bridge.imgmsg_to_cv2(data, "bgr8")
    except CvBridgeError as e:
        print(e)


def drive():
    global cv_image
    global op
    while cv_image is not None:
        cv2.imshow("origin", cv_image)
        img1, x_location, contours = process_image(cv_image)
        cv2.imshow('result', img1)

        if contours is not None:
            print('num circles', len(contours))

        if cv2.waitKey(1) & 0xFF == ord('q'):
            op = 'quit'
            break

        # if detect_obstacle():
        #     stop()
        #     # TODO send message detect obstacle
        #     break

        if contours is not None and len(contours) > 20:
            stop()
            # TODO send message arrive at turning point
            break

        if x_location is not None:
            pid = round(pidcal.pid_control(int(x_location)), 6)
            auto_drive(pid)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            op = 'quit'
            break


def auto_drive(pid):
    global car_run_speed
    w = 0
    if -0.065 < pid < 0.065:
        w = 1
    else:
        w = 0.7

    if car_run_speed < max_speed * w:
        car_run_speed += 0.002 * 10
    else:
        car_run_speed -= 0.003 * 10

    ack_msg = AckermannDriveStamped()
    ack_msg.header.stamp = rospy.Time.now()
    ack_msg.header.frame_id = ''
    ack_msg.drive.steering_angle = pid * turning_coef
    ack_msg.drive.speed = car_run_speed
    ack_publisher.publish(ack_msg)
    print('speed: ')
    print(car_run_speed)


def go(pid):
    ack_msg = AckermannDriveStamped()
    ack_msg.header.stamp = rospy.Time.now()
    ack_msg.header.frame_id = ''
    ack_msg.drive.steering_angle = pid
    ack_msg.drive.speed = max_speed
    ack_publisher.publish(ack_msg)


def back(pid):
    ack_msg = AckermannDriveStamped()
    ack_msg.header.stamp = rospy.Time.now()
    ack_msg.header.frame_id = ''
    ack_msg.drive.steering_angle = pid
    ack_msg.drive.speed = -0.4 if max_speed > 0.4 else -max_speed
    ack_publisher.publish(ack_msg)


def stop():
    ack_msg = AckermannDriveStamped()
    ack_msg.header.stamp = rospy.Time.now()
    ack_msg.header.frame_id = ''
    ack_msg.drive.steering_angle = 0
    ack_msg.drive.speed = 0
    ack_publisher.publish(ack_msg)


def go_strait():
    global op
    start_time = time.time()
    t = 1 / max_speed if max_speed != 0.0 else 0.0
    while time.time() - start_time < t:
        pid = 0
        go(pid)
        print(time.time() - start_time)
    stop()
    # TODO send message


def turn_left():
    global op
    start_time = time.time()
    t = 0.3 / max_speed if max_speed != 0.0 else 0.0
    while time.time() - start_time < t:
        pid = 0.0
        go(pid)
    start_time = time.time()
    t = 1.2 / max_speed if max_speed != 0.0 else 0.0
    while time.time() - start_time < t:
        pid = 0.34
        go(pid)
    stop()
    # TODO send message
    pass


def turn_right():
    global op
    # start_time = time.time()
    # t = 0.15 / max_speed if max_speed != 0 else 0
    # while time.time() - start_time < t:
    #     pid = 0
    #     back(pid)
    start_time = time.time()
    t = 1.3 / max_speed if max_speed != 0.0 else 0.0
    while time.time() - start_time < t:
        pid = -0.34
        go(pid)
    stop()
    # TODO send message
    pass


def main():
    global cv_image
    global ack_publisher
    global op
    rospy.sleep(3)
    bridge = CvBridge()
    image_sub = rospy.Subscriber("/usb_cam/image_raw/", Image, img_callback)

    rospy.init_node('auto_xycar', anonymous=True)

    # ack_publisher = rospy.Publisher('vesc/low_level/ackermann_cmd_mux/input/teleop', AckermannDriveStamped, queue_size=1)
    ack_publisher = rospy.Publisher('ackermann_cmd_mux/input/teleop', AckermannDriveStamped, queue_size=1)

    op = 'drive'
    flag = 0
    while cv_image is not None:
        print(op)

        if op == 'drive':
            drive()
        elif op == 'strait':
            go_strait()
        elif op == 'right':
            turn_right()
        elif op == 'left':
            turn_left()
        elif op == 'quit':
            stop()
            break
        else:
            break
        if cv2.waitKey(1) & 0xFF == ord('q'):
            stop()
            break

        if op == 'quit':
            break

        if run:
            flag += 1
            stop()
            time.sleep(2)

            if flag == 1:
                op = 'strait'
            elif flag == 2:
                op = 'drive'
            elif flag == 3:
                op = 'strait'
            elif flag == 4:
                op = 'drive'
            else:
                op = 'quit'
    print('end')
    try:
        rospy.spin()
    except KeyboardInterrupt:
        print("Shutting down")
    cv2.destroyAllWindows()


def process_image(frame):
    mask = processor.get_yellow_mask(frame, blurring=False, morphology=False, show=False)
    # grayscle
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    # blur
    kernel_size = 5
    blur_gray = cv2.GaussianBlur(gray, (kernel_size, kernel_size), 0)
    # canny edge
    low_threshold = 60  # 60
    high_threshold = 70  # 70
    edges_img = cv2.Canny(np.uint8(blur_gray), low_threshold, high_threshold)
    yellow_edges_img = cv2.bitwise_and(edges_img, edges_img, mask=mask)
    # warper
    img = warper.warp(yellow_edges_img)
    img1, x_location = slidewindow.slidewindow(img, show=False)

    contours = processor.find_contours(blur_gray[blur_gray.shape[0] / 2:blur_gray.shape[0], 0:blur_gray.shape[1] / 2],
                                       mask[mask.shape[0] / 2:mask.shape[0], 0:mask.shape[1] / 2], show=True)
    return img1, x_location, contours


if __name__ == '__main__':
    main()
