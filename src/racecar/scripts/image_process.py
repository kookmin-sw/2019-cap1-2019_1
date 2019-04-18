import cv2
import numpy as np

from slidewindow import SlideWindow
from warper import Warper


class ImageProcessor:
    def __init__(self):
        self.warper = Warper()
        self.slidewindow = SlideWindow()

        # color(yellow)
        self.lower_color = np.array([15, 100, 30])
        self.upper_color = np.array([30, 255, 255])

        # canny
        self.low_threshold = 300
        self.high_threshold = 500

    def get_right_lines(self, lines, img, draw_mean_line=False):
        if lines is None:
            return None, None

        rights_ht = []
        rights_ab = []
        r = lines.T[0]
        theta = lines.T[1]
        a = - np.tan(theta)
        b = r / np.cos(theta)
        top = 0
        mid = img.shape[0] / 2
        bot = img.shape[0]
        mean_top = np.mean(a * top + b)
        mean_mid = np.mean(a * mid + b)
        mean_bot = np.mean(a * bot + b)

        if draw_mean_line:
            cv2.line(img, (mean_top, top), (mean_bot, bot), 128, 2)
            cv2.imshow('mean_line', img)

        for line in lines:
            slope = - np.tan(line[0][1])
            bias = line[0][0] / np.cos(line[0][1])
            if (mean_top < slope * top + bias) and (mean_mid < slope * mid + bias) and (mean_bot < slope * bot + bias):
                rights_ht.append(line)
                rights_ab.append([slope, bias])

        return np.array(rights_ht), np.array(rights_ab)

    def cal_x_location(self, lines_ab):
        if lines_ab is None or len(lines_ab) == 0:
            return None

        y = 340
        return np.mean(lines_ab.T[0] * y + lines_ab.T[1])

    def yellow_mask(self, frame, blurring=False):
        if blurring:
            img = cv2.GaussianBlur(frame, (5, 5), 0)
        else:
            img = frame

        img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

        # create mask using color range
        img_mask = cv2.inRange(img_hsv, self.lower_color, self.upper_color)
        img_mask = cv2.bitwise_and(img, img, mask=img_mask)
        cv2.imshow("mask", img_mask)
        return img_mask

    def draw_line(self, img, lines, color=None):
        if color is None:
            color = 255
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

                    cv2.line(img, (x1, y1), (x2, y2), color, 2)

    def find_line(self, frame):
        # canny edge
        img_canny = cv2.Canny(np.uint8(frame), self.low_threshold, self.high_threshold, apertureSize=5)
        cv2.imshow("canny_line", img_canny)

        # find lines using houghlines and show them
        lines = cv2.HoughLines(img_canny, 1, np.pi / 180, 100)
        img = frame.copy()
        self.draw_line(img, lines, 255)
        cv2.imshow("lines", img)
        return img, lines

    def find_circle(self, frame):
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
        return img, circles
