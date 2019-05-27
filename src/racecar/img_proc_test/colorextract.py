import cv2 as cv
import numpy as np


hsv = 0
lower_color1 = 0
upper_color1 = 0
lower_color2 = 0
upper_color2 = 0
lower_color3 = 0
upper_color3 = 0


def mouse_callback(event, x, y, flags, param):
    global hsv, lower_color1, upper_color1, lower_color2, upper_color2, lower_color3, upper_color3

    # 마우스 왼쪽 버튼 누를시 위치에 있는 픽셀값을 읽어와서 HSV로 변환
    if event == cv.EVENT_LBUTTONDOWN:
        print(img_color[y, x])      #왼쪽버튼 눌렀을 때 가져온 color
        color = img_color[y, x]

        one_pixel = np.uint8([[color]])
        hsv = cv.cvtColor(one_pixel, cv.COLOR_BGR2HSV)
        hsv = hsv[0][0]

        # print(hsv)

        # HSV 색공간에서 마우스 클릭으로 얻은 픽셀값과 유사한 픽셀값의 범위를 정하기
        if hsv[0] < 10:
            print("case1")
            lower_color1 = np.array([hsv[0]-10+180, 30, 30])
            upper_color1 = np.array([180, 255, 255])

            lower_color2 = np.array([0, 30, 30])
            upper_color2 = np.array([hsv[0], 255, 255])

            lower_color3 = np.array([hsv[0], 30, 30])
            upper_color3 = np.array([hsv[0]+10, 255, 255])
            #     print(i-10+180, 180, 0, i)
            #     print(i, i+10)

        elif hsv[0] > 170:
            print("case2")
            lower_color1 = np.array([hsv[0], 30, 30])
            upper_color1 = np.array([180, 255, 255])

            lower_color2 = np.array([0, 30, 30])
            upper_color2 = np.array([hsv[0]+10-180, 255, 255])

            lower_color3 = np.array([hsv[0]-10, 30, 30])
            upper_color3 = np.array([hsv[0], 255, 255])
            #     print(i, 180, 0, i+10-180)
            #     print(i-10, i)
        else:
            print("case3")
            lower_color1 = np.array([hsv[0], 30, 30])
            upper_color1 = np.array([hsv[0]+10, 255, 255])

            lower_color2 = np.array([hsv[0]-10, 30, 30])
            upper_color2 = np.array([hsv[0], 255, 255])

            lower_color3 = np.array([hsv[0]-10, 30, 30])
            upper_color3 = np.array([hsv[0], 255, 255])
            #     print(i, i+10)
            #     print(i-10, i)

        print(hsv[0])
        print("@1", lower_color1, "~", upper_color1)
        print("@2", lower_color2, "~", upper_color2)
        print("@3", lower_color3, "~", upper_color3)


cv.namedWindow('img_color')
cv.setMouseCallback('img_color', mouse_callback)



while(True):
    img_color = cv.imread('images/block.jpg')
    height, width = img_color.shape[:2]
    img_color = cv.resize(img_color, (width, height), interpolation=cv.INTER_AREA)

    # 원본 영상을 HSV 영상으로 변환합니다.
    img_hsv = cv.cvtColor(img_color, cv.COLOR_BGR2HSV)

    # 범위 값으로 HSV 이미지에서 마스크를 생성합니다.
    img_mask1 = cv.inRange(img_hsv, lower_color1, upper_color1)
    img_mask2 = cv.inRange(img_hsv, lower_color2, upper_color2)
    img_mask3 = cv.inRange(img_hsv, lower_color3, upper_color3)
    img_mask = img_mask1 | img_mask2 | img_mask3


    # 마스크 이미지로 원본 이미지에서 범위값에 해당되는 영상 부분을 획득합니다.
    img_result = cv.bitwise_and(img_color, img_color, mask=img_mask)


    cv.imshow('img_color', img_color)
    cv.imshow('img_mask', img_mask)
    cv.imshow('img_result', img_result)


    # ESC 키누르면 종료
    if cv.waitKey(1) & 0xFF == 27:
        break


cv.destroyAllWindows()