#-*-coding:utf-8-*-

import cv2
import numpy as np

# 영상 받아서 흑백으로 변환
img = cv2.imread('images/block.jpg')

img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)


lower_color = np.array([0, 30, 30])
upper_color = np.array([30, 255, 255])

# 범위 값으로 HSV 이미지에서 마스크를 생성합니다.
img_mask = cv2.inRange(img_hsv, lower_color, upper_color)

cv2.imshow('img_mask', img_mask)
# img_mask = img_mask / 255
img_yellow = cv2.cvtColor(cv2.bitwise_and(img, img, mask=img_mask), cv2.COLOR_BGR2GRAY)
cv2.imshow('img_yellow', img_yellow)


print(img_mask.shape)

# circles = cv2.HoughCircles(img_gray, cv2.HOUGH_GRADIENT, 1, 30, param1=140,param2=20,minRadius=10, maxRadius=30)
circles = cv2.HoughCircles(img_yellow, cv2.HOUGH_GRADIENT, 1, 30, param1=140,param2=20,minRadius=10, maxRadius=30)


# img : 8비트 싱글 채널 GRAY 영상 이용
# dp(1) : 해상도 비율 (2이면 절반해상도)
# minDist(20) : 원의 중심과 가장 가까운 거리 (최소거리) 겹치느냐 안겹치느냐
# param1 : 임계값
# param2 : 중심점누산, 작으면 실패율 높고 크면 검출률 낮음
# min/maxRadius : 최소/최대 반지름


circles = np.uint16(np.around(circles))
# # 원 표시
for i in circles[0,:]:
    cv2.circle(img,(i[0],i[1]),i[2],(255,0,0),2)
    cv2.circle(img,(i[0],i[1]),1,(0,0,255),2)
        # img, 중심좌표, 반지름, 색상, 선두께

cv2.imshow('img', img)
# cv2.imshow('img_origin', img_origin)
cv2.waitKey(0)
cv2.destroyAllWindows()