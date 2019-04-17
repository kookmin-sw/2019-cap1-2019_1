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

from std_msgs.msg import String
from sensor_msgs.msg import Image
from cv_bridge import CvBridge, CvBridgeError

from ackermann_msgs.msg import AckermannDriveStamped

warper = Warper()
slidewindow = SlideWindow()
pidcal = PidCal()

q1 = que.Queue()
bridge = CvBridge()

cv_image = None
ack_publisher = None
car_run_speed = 0.5

# color(yellow)
lower_color = np.array([0, 150, 30])
upper_color = np.array([30, 255, 255])

# canny
low_threshold = 300
high_threshold = 500


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
        yellow_img = preprocessing(cv_image, warping=True, blurring=True)
        img1, x_location = process_image(yellow_img)
        cv2.imshow('origin', cv_image)
        find_line(yellow_img)
        find_circle(yellow_img)
        if x_location is not None:
            pid = round(pidcal.pid_control(int(x_location)), 6)
            print(pid)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
        cv2.imshow("result", img1)

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


def preprocessing(frame, warping=False, blurring=False):
    if blurring:
        img = cv2.GaussianBlur(frame, (5, 5), 0)
    else:
        img = frame

    if warping:
        # warper
        img = warper.warp(img)

    img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    # create mask using color range
    img_mask = cv2.inRange(img_hsv, lower_color, upper_color)
    img_mask = cv2.bitwise_and(img, img, mask=img_mask)
    cv2.imshow("mask", img_mask)

    # mask to gray
    img_graymask = cv2.cvtColor(img_mask, cv2.COLOR_BGR2GRAY)
    return img_graymask


def draw_line(img, lines):
    if lines is not None:
        for line, i in zip(lines, range(len(lines))):
            for rho, theta in line:
                a = np.cos(theta)
                b = np.sin(theta)
                x0 = a * rho
                y0 = b * rho
                x1 = int(x0 + 1000 * (-b))
                y1 = int(y0 + 1000 * (a))
                x2 = int(x0 - 1000 * (-b))
                y2 = int(y0 - 1000 * (a))

                cv2.line(img, (x1, y1), (x2, y2), 255 / (i + 1), 2)


def find_line(frame):
    # canny edge
    img_canny = cv2.Canny(np.uint8(frame), low_threshold, high_threshold, apertureSize=5)
    cv2.imshow("canny_line", img_canny)

    # find lines using houghlines and show them
    lines = cv2.HoughLines(img_canny, 1, np.pi / 180, 100)
    img = frame.copy()
    draw_line(img, lines)
    cv2.imshow("lines", img)
    return img, lines


def find_circle(frame):
    # canny edge (for debug)
    # img_canny = cv2.Canny(np.uint8(img_graymask), 10, 20, apertureSize=3)
    # cv2.imshow("canny_circle", img_canny)

    # find circle using houghcircle
    img = frame.copy()
    circles = cv2.HoughCircles(img, cv2.HOUGH_GRADIENT, 1, 15, param1=20, param2=10, minRadius=3, maxRadius=10)

    # show circles
    if circles is not None:
        circles = np.uint16(np.around(circles))
        for i in circles[0, :]:
            cv2.circle(img, (i[0], i[1]), i[2], 255, 2)  # circle
            cv2.circle(img, (i[0], i[1]), 1, 192, 2)  # center
    cv2.imshow("circles", img)
    return img


if __name__ == '__main__':
    main()
