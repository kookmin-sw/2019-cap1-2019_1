#-*-coding:utf-8-*-
import cv2
import numpy as np

# img = cv2.imread("images/block.jpg", cv2.IMREAD_COLOR)
img = cv2.imread("images/block.jpg", cv2.IMREAD_COLOR)
# img = cv2.medianBlur(img,3)

gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

canny = cv2.Canny(img, 100, 200, apertureSize=3)
# (이미지, 임계값1, 임계값2, 커널크기, L2그라디언트)
# 임계값1 : 이 값 이하면 제외함
# 임계값2 : 이 값 이상면 포함함
# 커널 크기 : Sobel 마스크의 Aperture Size 설정 (안하면 디폴트)
# L2그라디언트 : L2방식의 사용유무 설정 (안하면 디폴트) L2는 식이 있음,,

sobel = cv2.Sobel(gray, cv2.CV_8U, 1, 0, 3)
# (흑백이미지, 정밀도, x미분, y미분, 커널, 배율, 델타, 픽셀 외삽법)
# 정밀도 : 이미지 정밀도 -> 이 값에 따라 결과물 달라질 수 있음
# x,y방향 미분 : 합이 1 이상이어야 하며, 각각의 값은 0보다 커야함
# 커널 : 소벨커널의 크기 (1*1, 3*3, 5*5, ... -> 1, 3, 5, 7 사용)
# 배율, 델타 : 계산 전 미분값에 대한 배율값, 추가값
#픽셀 외삽법 : 이미지 가장자리 처리할 경우 영역 밖 픽셀을 추정해서 할당해야함 -> 설정한다


lines = cv2.HoughLines(canny, 1, np.pi/90, 100)
for line in lines:
    for rho, theta in line:
        a = np.cos(theta)
        b = np.sin(theta)
        x0 = a*rho
        y0 = b*rho
        x1 = int(x0 + 1000*(-b))
        y1 = int(y0 + 1000*(a))
        x2 = int(x0 - 1000*(-b))
        y2 = int(y0 - 1000*(a))


        cv2.line(img,(x1,y1),(x2,y2),(0,0,255),2)


# lines = cv2.HoughLinesP(canny,1,np.pi/360, 10, minLineLength=10, maxLineGap=0)
# (img, r값, theta값, threshold값, 선최소길이, 선사이최대허용간격)


# for i in range(len(lines)):
#     for x1,y1,x2,y2 in lines[i]:
#         cv2.line(img,(x1,y1),(x2,y2),(0,0,255),3)



cv2.imshow("canny", canny)
# cv2.imshow("sobel", sobel)
# cv2.imshow("laplacian", laplacian)
cv2.imshow("line", img)


cv2.waitKey(0)
cv2.destroyAllWindows()
