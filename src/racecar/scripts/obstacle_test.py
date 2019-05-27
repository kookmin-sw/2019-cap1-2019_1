#!/usr/bin/env python
import cv2
import Queue as que

import sys
import rospy

from obstacle import detect_obstacle

from sensor_msgs.msg import Image
from cv_bridge import CvBridge, CvBridgeError

q1 = que.Queue()
bridge = CvBridge()

cv_image = None
ack_publisher = None


def signal_handler(signal, frame):
    print 'You pressed Ctrl+C!'
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
    # signal.signal(signal.SIGINT, signal_handler)

    while cv_image is not None:
        img = cv_image.copy()
        if detect_obstacle():
            cv2.rectangle(img, (img.shape[1] / 2 - 150, 100), (img.shape[1] / 2 + 150, 400), (0, 0, 255), -1)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
        cv2.imshow('img', img)

    try:
        rospy.spin()
    except KeyboardInterrupt:
        print("Shutting down")
    cv2.destroyAllWindows()


if __name__ == '__main__':
    main()
