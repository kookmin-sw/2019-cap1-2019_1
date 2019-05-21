import cv2
import numpy as np
from sklearn.cluster import DBSCAN

from slidewindow import SlideWindow


class ImageProcessor:
    def __init__(self, clustering_op='theta'):
        self.slidewindow = SlideWindow()

        # color(yellow)
        self.lower_color = np.array([10, 70, 20])
        self.upper_color = np.array([45, 255, 255])

        self.kernel_33 = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (3, 3))
        self.kernel_55 = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (5, 5))

        self.clustering_op = clustering_op
        eps = 0.0
        if clustering_op == 'pos':
            eps = 5
        elif clustering_op == 'theta':
            eps = np.pi / 12
        self.dbscan = DBSCAN(eps=eps, min_samples=3)

    def yellow_mask(self, frame, blurring=False, morphology=False, show=False, window_name='yeloow_img'):
        if blurring:
            img = cv2.GaussianBlur(frame, (5, 5), 0)
        else:
            img = frame

        img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

        # create mask using color range
        mask = cv2.inRange(img_hsv, self.lower_color, self.upper_color)

        if morphology:
            mask = cv2.morphologyEx(mask, cv2.MORPH_OPEN, self.kernel_55)
            mask = cv2.morphologyEx(mask, cv2.MORPH_CLOSE, self.kernel_55)

        img_res = cv2.bitwise_and(img, img, mask=mask)
        if show:
            cv2.imshow(window_name, img_res)
        return img_res

    def get_yellow_mask(self, frame, blurring=False, morphology=False, show=False, window_name='mask'):
        if blurring:
            img = cv2.GaussianBlur(frame, (5, 5), 0)
        else:
            img = frame

        img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

        # create mask using color range
        mask = cv2.inRange(img_hsv, self.lower_color, self.upper_color)

        if morphology:
            mask = cv2.morphologyEx(mask, cv2.MORPH_DILATE, self.kernel_55)
        if show:
            cv2.imshow(window_name, mask)
        return mask

    def find_line(self, edges_img, show=False, window_name='lines'):
        # find lines using houghlines and show them
        lines = cv2.HoughLines(edges_img, 2, np.pi / 180, 200)
        if show:
            img = edges_img.copy()
            self.draw_lines(img, lines, 255)
            cv2.imshow(window_name, img)
        return lines

    def clustering(self, lines, img, show=False, window_name='clustering'):
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
        if show:
            if max(self.dbscan.labels_) == 0:
                step = 0
            else:
                step = 128 / np.max(self.dbscan.labels_)
            for i in range(len(lines)):
                if self.dbscan.labels_[i] == -1:
                    continue
                color = 127 + step * self.dbscan.labels_[i]
                self.draw_line(img, lines[i], color=color)

                cv2.imshow(window_name, img)

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

    def get_right_line(self, lines):
        if lines is None:
            return None
        max_value = 0
        max_idx = 0
        for i in range(len(lines)):
            if self.cal_x_location(self.polar2ab(lines[i])) > max_value:
                max_value = abs(lines[i][0][0])
                max_idx = i
        return lines[max_idx]

    @staticmethod
    def polar2ab(line):
        if line is None:
            return None
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
        return np.mean(lines_ab[0] * y + lines_ab[1])

    @staticmethod
    def draw_line(img, line, color=255):
        if line is None:
            return
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
    def find_circle(gray_img, mask, show=False, window_name='circles', show_edge=False, edge_window_name='circle_edge'):
        # find circle using houghcircle
        param1 = 30
        param2 = 5
        circles = cv2.HoughCircles(gray_img, cv2.HOUGH_GRADIENT, 1.3, 10, param1=param1, param2=param2, minRadius=2,
                                   maxRadius=5)

        edges_img = cv2.Canny(gray_img, param1 / 2, param1)
        edges_img = cv2.bitwise_and(edges_img, edges_img, mask=mask)

        if show_edge:
            cv2.imshow(edge_window_name, edges_img)

        circles_ = [[]]

        if circles is not None:
            for circle in circles[0]:
                x = int(circle[0])
                y = int(circle[1])
                if x < 0 or mask.shape[1] - 1 < x or y < 0 or mask.shape[0] - 1 < y:
                    continue
                if mask[y][x] > 0:
                    circles_[0].append(circle)

        if show:
            # show circles
            img1 = gray_img.copy()
            if circles is not None:
                circles = np.uint16(np.around(circles))
                for i in circles[0, :]:
                    cv2.circle(img1, (i[0], i[1]), i[2], 255, 2)  # circle
                    cv2.circle(img1, (i[0], i[1]), 1, 192, 2)  # center
            cv2.imshow('circle_non_filter', img1)

            img2 = gray_img.copy()
            if circles_ is not None:
                circles_ = np.uint16(np.around(circles_))
                for i in circles_[0, :]:
                    cv2.circle(img2, (i[0], i[1]), i[2], 255, 2)  # circle
                    cv2.circle(img2, (i[0], i[1]), 1, 192, 2)  # center
            cv2.imshow(window_name, img2)

        return circles_

    def find_contours(self, gray_img, mask, show=False):
        edges_img = cv2.Canny(gray_img, 30 / 2, 30)
        edges_img = cv2.bitwise_and(edges_img, edges_img, mask=mask)
        edges_img = cv2.morphologyEx(edges_img, cv2.MORPH_CLOSE, kernel=self.kernel_33)

        if show:
            cv2.imshow('contour_edges_img', edges_img)

        img1, img2, img3 = None, None, None
        if show:
            img1 = gray_img.copy()
            img2 = gray_img.copy()
            img3 = gray_img.copy()
        image, contours, hierarchy = cv2.findContours(edges_img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

        hulls = []

        contours_ = []
        for cnt, i in zip(contours, range(len(contours))):
            hull = cv2.convexHull(cnt)
            hulls.append(hull)
            # epsilon = 0.05*cv2.arcLength(cnt, True)
            # approx = cv2.approxPolyDP(cnt, epsilon, True)
            area = cv2.contourArea(hull)
            x, y, w, h = cv2.boundingRect(hull)
            aspect = float(w) / h
            extend = float(area) / (w * h)
            if 30 < area < 600 and 0.25 < aspect < 4 and extend > 0.5:
                contours_.append(hull)

        print('contours', len(contours))
        print('hulls', len(hulls))
        print('contours_', len(contours_))

        if show:
            image1 = cv2.drawContours(img1, contours, -1, 255, 3)
            cv2.imshow('contours', image1)

        if show:
            image2 = cv2.drawContours(img2, hulls, -1, 255, 3)
            cv2.imshow('hulls', image2)

        if show:
            image3 = cv2.drawContours(img3, contours_, -1, 255, 3)
            cv2.imshow('contours_', image3)

        return contours_
