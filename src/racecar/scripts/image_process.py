import cv2
import numpy as np
from sklearn.cluster import DBSCAN

from slidewindow import SlideWindow


def get_right_line(lines):
    if lines is None:
        return None
    max_value = 0
    max_idx = 0
    for i in range(len(lines)):
        if abs(lines[i][0][0]) > max_value:
            max_value = abs(lines[i][0][0])
            max_idx = i
    return lines[max_idx]


class ImageProcessor:
    def __init__(self, clustering_op='theta'):
        self.slidewindow = SlideWindow()

        # color(yellow)
        self.lower_color = np.array([15, 50, 20])
        self.upper_color = np.array([30, 255, 255])

        self.kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (11, 11))

        self.clustering_op = clustering_op
        eps = 0.0
        if clustering_op == 'pos':
            eps = 5
        elif clustering_op == 'theta':
            eps = np.pi / 12
        self.dbscan = DBSCAN(eps=eps, min_samples=3)

    def yellow_mask(self, frame, blurring=False, morphology=False):
        if blurring:
            img = cv2.GaussianBlur(frame, (5, 5), 0)
        else:
            img = frame

        img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

        # create mask using color range
        mask = cv2.inRange(img_hsv, self.lower_color, self.upper_color)

        if morphology:
            mask = cv2.morphologyEx(mask, cv2.MORPH_OPEN, self.kernel)
            mask = cv2.morphologyEx(mask, cv2.MORPH_CLOSE, self.kernel)

        img_res = cv2.bitwise_and(img, img, mask=mask)
        cv2.imshow("mask", img_res)
        return img_res

    def find_line(self, edges_img):
        # find lines using houghlines and show them
        lines = cv2.HoughLines(edges_img, 1, np.pi / 180, 150)
        img = edges_img.copy()
        self.draw_lines(img, lines, 255)
        return img, lines

    def clustering(self, lines, img, op='pos'):
        if lines is None:
            return
        data = []
        for line in lines:
            if self.clustering_op == 'pos':
                r = line[0][0]
                theta = line[0][1]
                x = np.cos(theta) * r
                y = np.sin(theta) * r
                data.append([x, y])
            elif self.clustering_op == 'theta':
                theta = line[0][1] - np.pi / 2
                if theta < 0:
                    theta += np.pi
                data.append(theta)
        data = np.array(data)
        if self.clustering_op == 'theta':
            data = data.reshape(-1, 1)

        self.dbscan.fit(data)
        print(self.dbscan.labels_)
        if max(self.dbscan.labels_) == 0:
            step = 0
        else:
            step = 128 / np.max(self.dbscan.labels_)
        for i in range(len(lines)):
            if self.dbscan.labels_[i] == -1:
                continue
            rho = lines[i][0][0]
            theta = lines[i][0][1]
            color = 127 + step * self.dbscan.labels_[i]
            a = np.cos(theta)
            b = np.sin(theta)
            x0 = a * rho
            y0 = b * rho
            x1 = int(x0 + 1000 * (-b))
            y1 = int(y0 + 1000 * (a))
            x2 = int(x0 - 1000 * (-b))
            y2 = int(y0 - 1000 * (a))

            cv2.line(img, (x1, y1), (x2, y2), color, 2)

    def get_main_lines(self, lines):
        if lines is None:
            return None

        n = max(self.dbscan.labels_)
        if n == -1:
            return None
        count = np.zeros(n + 1, dtype=int)
        for i in self.dbscan.labels_:
            if i != -1:
                count[i] += 1

        main_label = np.argmax(count)

        main_lines = []
        for i in range(len(lines)):
            if self.dbscan.labels_[i] == main_label:
                main_lines.append(lines[i])
        return main_lines

    @staticmethod
    def polar2ab(line):
        r = line[0][0]
        theta = line[0][1]
        a = - np.tan(theta)
        b = r / np.cos(theta)
        return [a, b]

    @staticmethod
    def cal_x_location(lines_ab):
        if lines_ab is None or len(lines_ab) == 0:
            return None

        y = 340
        return np.mean(lines_ab.T[0] * y + lines_ab.T[1])

    @staticmethod
    def draw_line(img, line, color=255):
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

    def draw_lines(self, img, lines, color=None):
        if color is None:
            color = 255
        if lines is not None:
            for line, i in zip(lines, range(len(lines))):
                self.draw_line(img, line, color)

    @staticmethod
    def find_circle(frame):
        # find circle using houghcircle
        img = frame.copy()
        circles = cv2.HoughCircles(img, cv2.HOUGH_GRADIENT, 1, 15, param1=20, param2=10, minRadius=3, maxRadius=10)

        # show circles
        if circles is not None:
            circles = np.uint16(np.around(circles))
            for i in circles[0, :]:
                cv2.circle(img, (i[0], i[1]), i[2], 255, 2)  # circle
                cv2.circle(img, (i[0], i[1]), 1, 192, 2)  # center
        return img, circles
