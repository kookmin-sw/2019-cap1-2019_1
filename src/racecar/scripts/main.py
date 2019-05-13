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
max_speed = 0.5
car_run_speed = max_speed * 0.5

op = None


def img_callback(data):
    global cv_image
    try:
        cv_image = bridge.imgmsg_to_cv2(data, "bgr8")
    except CvBridgeError as e:
        print(e)


def is_turning_point():
    pass


def drive():
    global cv_image
    global op
    circles = 0
    while cv_image is not None:
        if is_turning_point():
            # TODO send arrive turning_point massage
            break
        # if detect_obstacle():
        #     # TODO send obstacle detecting message
        #     continue

        cv2.imshow('origin', cv_image)

        yellow_img = processor.yellow_mask(cv_image, blurring=False, morphology=False, show=False)
        gray_img = cv2.cvtColor(yellow_img, cv2.COLOR_BGR2GRAY)
        cv2.imshow('yellow', yellow_img)

        edges_img = cv2.Canny(gray_img, 100, 200, apertureSize=3)
        cv2.imshow('edges', edges_img)
        warp_img = warper.warp(edges_img)
        cv2.imshow('warp', warp_img)

        # warp_img = warper.warp(gray_img)
        # cv2.imshow('warp', warp_img)
        #
        # edges_img = cv2.Canny(warp_img, 100, 200, apertureSize=3)
        # cv2.imshow('edges', edges_img)

        preprocessed_img = warp_img
        # preprocessed_img = edges_img

        lines = processor.find_line(preprocessed_img, show=True)

        cluster_img = preprocessed_img.copy()
        processor.clustering(lines, cluster_img, show=True)

        main_lines = processor.get_main_lines(lines)
        main_lines_img = preprocessed_img.copy()
        processor.draw_lines(main_lines_img, main_lines)
        cv2.imshow('main_lines', main_lines_img)

        right_line = processor.get_right_line(main_lines)
        right_line_img = preprocessed_img.copy()
        processor.draw_line(right_line_img, right_line)
        cv2.imshow('right_line', right_line_img)

        right_ab = processor.polar2ab(right_line)
        x_location = processor.cal_x_location(right_ab)

        if x_location is not None:
            img_x_location = preprocessed_img.copy()
            cv2.rectangle(img_x_location, (int(x_location) - 30, 310), (int(x_location) + 30, 370), 255, -1)
            cv2.imshow('x_location', img_x_location)
            x_location += cv_image.shape[1] * 0.175
            pid = round(pidcal.pid_control(int(x_location)), 6)
            auto_drive(pid)

        circles = processor.find_circle(warp_img, show=True)


        if cv2.waitKey(1) & 0xFF == ord('q'):
            op = 'quit'
            break


def drive_():
    global cv_image
    global op
    while cv_image is not None:
        cv2.imshow("origin", cv_image)
        img1, x_location , circles = process_image(cv_image)
        cv2.imshow('result', img1)

        if circles is not None and len(circles) > 10:
            stop()
            # TODO send massage arrive at turning point
            sleep(5)
            break

        if x_location is not None:
            pid = round(pidcal.pid_control(int(x_location)), 6)
            # auto_drive(pid)

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
    ack_msg.drive.steering_angle = pid
    ack_msg.drive.speed = car_run_speed
    ack_publisher.publish(ack_msg)
    print('speed: ')
    print(car_run_speed)


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
    while time.time() - start_time < 3:
        pid = 0
        auto_drive(pid)
        print(time.time() - start_time)
    stop()
    # TODO send message
    op = 'quit'


def turn_left():
    global op
    start_time = time.time()
    while time.time() - start_time < 1:
        pid = 0
        auto_drive(pid)
    start_time = time.time()
    while time.time() - start_time < 3.5:
        pid = 0.34
        auto_drive(pid)
    stop()
    op = 'quit'
    # TODO send message
    pass


def turn_right():
    global op
    start_time = time.time()
    while time.time() - start_time < 3:
        pid = -0.34
        auto_drive(pid)
    stop()
    # TODO send message
    op = 'quit'
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

    op = 'left'
    while cv_image is not None:
        print(op)

        if op == 'drive':
            drive_()
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


    print('end')
    try:
        rospy.spin()
    except KeyboardInterrupt:
        print("Shutting down")
    cv2.destroyAllWindows()


def process_image(frame):
    yellow_img = processor.yellow_mask(frame, blurring=False, morphology=False, show=True)
    # grayscle
    gray = cv2.cvtColor(yellow_img, cv2.COLOR_BGR2GRAY)
    # blur
    kernel_size = 5
    blur_gray = cv2.GaussianBlur(gray, (kernel_size, kernel_size), 0)
    # canny edge
    low_threshold = 60  # 60
    high_threshold = 70  # 70
    edges_img = cv2.Canny(np.uint8(blur_gray), low_threshold, high_threshold)
    # warper
    img = warper.warp(edges_img)
    img1, x_location = slidewindow.slidewindow(img)


    circles = processor.find_circle(warper.warp(gray), show=True)

    return img1, x_location, circles


if __name__ == '__main__':
    main()
