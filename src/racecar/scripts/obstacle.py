import numpy as np

import rospy
from std_msgs.msg import LaserScan

lidar_data = None
rangeH = 0.5
rangeV = 1.0


def lidar_callback(Data):
    pass


def detect_obstacle():
    lasdar_sub = rospy.Subscriber('/scan/', LaserScan, lidar_callback)

    if lidar_data is None:
        return None

    for i in range(10, 170):
        t = i / 180 * np.pi
        l = lidar_data.ranges[i]

        if l * np.sin(t) < rangeH and l * np.cos(t) < rangeV:
            return True

    return False
