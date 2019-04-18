#!/usr/bin/env python
import cv2
import threading
import Queue as que
import time
import numpy as np

import roslib
import sys
import rospy
import signal
import importlib
import cPickle
import genpy.message
from rospy import ROSException
import sensor_msgs.msg
import actionlib
import rostopic
import rosservice
from rosservice import ROSServiceException

from slidewindow import SlideWindow
from warper import Warper
from pidcal import PidCal
from image_process import *

from std_msgs.msg import String
from sensor_msgs.msg import Image
from cv_bridge import CvBridge, CvBridgeError

from ackermann_msgs.msg import AckermannDriveStamped

warper = Warper()
slidewindow = SlideWindow()
pidcal = PidCal()
processor = ImageProcessor()

q1 = que.Queue()
bridge = CvBridge()

cv_image = None
ack_publisher = None
car_run_speed = 0.5

width = 0
height = 0


def signal_handler(signal, frame):
    print('You pressed Ctrl+C!')
    sys.exit(0)


def img_callback(data):
    global cv_image
    try:
        cv_image = bridge.imgmsg_to_cv2(data, "bgr8")
    except CvBridgeError as e:
        print(e)


def main():
    global cv_image
    global ack_publisher
    rospy.sleep(3)
    bridge = CvBridge()
    image_sub = rospy.Subscriber("/usb_cam/image_raw/", Image, img_callback)

    rospy.init_node('auto_xycar', anonymous=True)

    while cv_image is not None:
        global width
        width = cv_image.shape[1]
        global height
        height = cv_image.shape[0]
        yellow_img = processor.preprocessing(cv_image, warping=True, blurring=True)
        # img1, x_location = process_image(yellow_img)
        cv2.imshow('origin', cv_image)
        img_lines, lines = processor.find_line(yellow_img)
        rights_ht, rights_ab = processor.get_right_lines(lines, img_lines)

        img_right = yellow_img.copy()
        processor.draw_line(img_right, rights_ht, 255)
        cv2.imshow('rights', img_right)

        x_location = processor.cal_x_location(rights_ab)
        img_x_location = yellow_img.copy()
        cv2.rectangle(img_x_location, (x_location - 30, 310), (x_location + 30, 370), 255, -1)
        cv2.imshow('x_location', img_x_location)
        x_location = int(x_location + width * 0.175)

        img_circles, circles = processor.find_circle(yellow_img)
        if x_location is not None:
            pid = round(pidcal.pid_control(int(x_location)), 6)
            print(pid)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
        # cv2.imshow("result", img1)

    try:
        rospy.spin()
    except KeyboardInterrupt:
        print("Shutting down")
    cv2.destroyAllWindows()


def process_image(frame):
    low_threshold = 20
    high_threshold = 70
    edges_img = cv2.Canny(np.uint8(frame), low_threshold, high_threshold)
    cv2.imshow("proc edge", edges_img)
    # warper
    # img = warper.warp(edges_img)
    img1, x_location = slidewindow.slidewindow(edges_img)

    return img1, x_location


if __name__ == '__main__':
    main()
