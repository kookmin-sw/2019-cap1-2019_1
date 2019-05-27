import cv2
import numpy as np
import matplotlib.pyplot as plt
from scipy.interpolate import *
from matplotlib.pyplot import *
from sklearn.cluster import DBSCAN


class SlideWindow:
    def __init__(self):
        self.left_fit = None
        self.right_fit = None
        self.leftx = None
        self.rightx = None
        self.dbscanL = DBSCAN(eps=3, min_samples=5)
        self.dbscanS = DBSCAN(eps=2, min_samples=5)

    def slidewindow(self, img, show=False):

        x_location = None
        # init out_img, height, width        
        out_img = np.dstack((img, img, img)) * 255
        height = img.shape[0]
        width = img.shape[1]

        # num of windows and init the height
        window_height = 5
        nwindows = 30

        # find nonzero location in img, nonzerox, nonzeroy is the array flatted one dimension by x,y
        nonzero = img.nonzero()
        # print(nonzero)
        nonzeroy = np.array(nonzero[0])
        nonzerox = np.array(nonzero[1])
        # print(nonzerox)
        # init data need to sliding windows
        margin = 20
        minpix = 5
        good_lane_inds = []
        right_lane_inds = []

        # first location and segmenation location finder
        # draw line
        pts_left = np.array([[width / 2 - 50, height], [width / 2 - 50, height - 60], [width / 2 - 130, height - 80],
                             [width / 2 - 130, height]], np.int32)
        cv2.polylines(out_img, [pts_left], False, (0, 255, 0), 1)

        pts_catch = np.array([[0, 340], [width, 340]], np.int32)
        cv2.polylines(out_img, [pts_catch], False, (0, 120, 120), 1)

        # indicies before start line(the region of pts_left)
        left_inds = ((nonzerox >= width / 2 - 130) & (nonzeroy >= nonzerox * 0.33 + 337) & (
                nonzerox <= width / 2 - 50)).nonzero()[0]
        good_inds = []

        left_img = None
        good_img = None
        if show:
            left_img = out_img.copy()
            good_img = out_img.copy()

        if len(left_inds) > 0:
            if show:
                for i in left_inds:
                    cv2.circle(left_img, (nonzerox[i], nonzeroy[i]), 1, (255, 0, 0), -1)

            self.dbscanL.fit(nonzerox[left_inds].reshape(-1, 1))

            n = max(self.dbscanL.labels_)
            min_value = img.shape[1]
            min_index = 0
            for i in range(n):
                mean = np.mean(nonzerox[left_inds[np.where(self.dbscanL.labels_ == i)]])
                if mean < min_value:
                    min_value = mean
                    min_index = i

            good_inds = left_inds[np.where(self.dbscanL.labels_ == min_index)]

            if show:
                for i in good_inds:
                    cv2.circle(good_img, (nonzerox[i], nonzeroy[i]), 1, (255, 0, 0), -1)

        # left line exist, lefty current init
        line_exist_flag = None
        y_current = None
        x_current = None
        x_current_old = None
        good_center_inds = None
        p_cut = None

        # check the minpix before left start line
        # if minpix is enough on left, draw left, then draw right depends on left
        # else draw right, then draw left depends on right
        if len(good_inds) > minpix:
            line_flag = 1
            x_current = np.int(np.mean(nonzerox[good_inds]))
            x_current_old = x_current
            y_current = np.int(np.mean(nonzeroy[good_inds]))
            max_y = y_current
        else:
            line_flag = 3
            # indicies before start line(the region of pts_center)
            # good_center_inds = ((nonzeroy >= nonzerox * 0.45 + 132) & (nonzerox >= width/2 - 60) & (nonzerox <= width/2 + 90)).nonzero()[0]
            # p_cut is for the multi-demensional function
            # but curve is mostly always quadratic function so i used polyfit( , ,2)
        #    if nonzeroy[good_center_inds] != [] and nonzerox[good_center_inds] != []:
        #        p_cut = np.polyfit(nonzeroy[good_center_inds], nonzerox[good_center_inds], 2)

        if line_flag != 3:
            # it's just for visualization of the valid inds in the region
            for i in range(len(good_inds)):
                img = cv2.circle(out_img, (nonzerox[good_inds[i]], nonzeroy[good_inds[i]]), 1, (0, 255, 0), -1)
            # window sliding and draw
            for window in range(0, nwindows):
                if line_flag == 1:
                    # rectangle x,y range init
                    win_y_low = y_current - (window + 1) * window_height
                    win_y_high = y_current - window * window_height
                    win_x_low = x_current - margin
                    win_x_high = x_current + margin
                    # draw rectangle
                    # 0.33 is for width of the road
                    cv2.rectangle(out_img, (win_x_low, win_y_low), (win_x_high, win_y_high), (0, 255, 0), 1)
                    cv2.rectangle(out_img, (win_x_low + int(width * 0.33), win_y_low),
                                  (win_x_high + int(width * 0.33), win_y_high), (255, 0, 0), 1)
                    # indicies of dots in nonzerox in one square
                    inds = ((nonzeroy >= win_y_low) & (nonzeroy < win_y_high) & (nonzerox >= win_x_low) & (
                            nonzerox < win_x_high)).nonzero()[0]

                    if show:
                        for i in inds:
                            cv2.circle(left_img, (nonzerox[i], nonzeroy[i]), 1, (255, 0, 0), -1)

                    slope = (x_current - x_current_old) / float(win_y_high - win_y_low)
                    x_predict = x_current + (slope * -window_height)
                    x_current_old = x_current

                    # check num of indicies in square and put next location to current
                    if len(inds) > minpix:
                        if np.abs(x_predict - np.mean(nonzerox[inds])) > 10:
                            self.dbscanS.fit(nonzerox[inds].reshape(-1, 1))

                            n = max(self.dbscanS.labels_) + 1
                            min_value = float('inf')
                            min_index = 0.0
                            for i in range(n):
                                inds_i = inds[np.where(self.dbscanS.labels_ == i)]

                                if show:
                                    for idx in inds_i:
                                        cv2.circle(left_img, (nonzerox[idx], nonzeroy[idx]), 1, (0, 0, 255 / n * (i + 1)), -1)

                                error = np.abs(x_predict - np.mean(nonzerox[inds_i]))
                                if error < min_value:
                                    min_value = error
                                    min_index = i

                            good_inds = inds[np.where(self.dbscanS.labels_ == min_index)]
                        else:
                            if show:
                                for idx in inds:
                                    cv2.circle(left_img, (nonzerox[idx], nonzeroy[idx]), 1, (255, 0, 0), -1)
                            good_inds = inds

                        if show:
                            for i in good_inds:
                                cv2.circle(good_img, (nonzerox[i], nonzeroy[i]), 1, (255, 0, 0), -1)

                        if len(good_inds) > minpix:
                            x_current = np.int(np.mean(nonzerox[good_inds]))
                        elif nonzeroy[good_lane_inds] != [] and nonzerox[good_lane_inds] != []:
                            p_left = np.polyfit(nonzeroy[good_lane_inds], nonzerox[good_lane_inds], 2)
                            x_current = np.int(np.polyval(p_left, win_y_high))
                    elif nonzeroy[good_lane_inds] != [] and nonzerox[good_lane_inds] != []:
                        p_left = np.polyfit(nonzeroy[good_lane_inds], nonzerox[good_lane_inds], 2)
                        x_current = np.int(np.polyval(p_left, win_y_high))
                    # 338~344 is for recognize line which is yellow line in processed image(you can check in imshow)
                    if 338 <= win_y_low < 344:
                        # 0.165 is the half of the road(0.33)
                        x_location = x_current + int(width * 0.175)

                good_lane_inds.extend(good_inds)

        if show:
            cv2.imshow('goods', good_img)
            cv2.imshow('inds', left_img)

        return out_img, x_location
