import rospy
from sensor_msgs.msg import Imu

def callback(msg):
        print "Quaternoion ========"
        print "x : ", msg.orientation.x
        print "y : ", msg.orientation.y

rospy.init_node('get_imu_data')
sub = rospy.Subscriber('imu', Imu, callback)
rospy.spin()