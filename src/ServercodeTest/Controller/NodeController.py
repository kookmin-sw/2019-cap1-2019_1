import os
import sys
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))

from flask import jsonify
from ServiceLogic.NodeServiceLogic import NodeServiceLogic
#json request
from flask import request
from flask import json

#import Blueprint
from flask import Blueprint
NodeController_index=Blueprint('NodeController_index',__name__)

#import Node
from Domain.Node import Node

nodeServiceLogic = NodeServiceLogic()

#전체노드불러오기(검증완료)
#혹시 안드로이드에서 이거호출했는데 문제생기면 methods get에서 post로 바꿔서 그런거
@NodeController_index.route('/getAllNodes', methods=['POST'])
def getAllNodes():
    request_json = request.get_json()

    phone_origin_number="None"
    identification="None"
    for firstkey, value in request_json.items():
        #관리자인지 유저인지 식별용
        if firstkey=="identification":
            identification=value
        elif firstkey=="phone_origin_number":
            phone_origin_number=value

    node_list=nodeServiceLogic.getAllNodes(identification,phone_origin_number)

    datas = []

    for node in node_list:

        node_neighbors_datas=[]
        # print("NodeController의 getAllNodes시작")
        # print(node.node_id)

        for node_neighbor in node.neighbors:
            # print(node_neighbor.node_id)
            node_neighbors_datas.append(node_neighbor.node_id)

        data = {'node_id': node.node_id, 'node_type': node.node_type, 'node_name': node.node_name, 'pos_x': node.pos_x,
                'pos_y': node.pos_y, 'node_neighbors': node_neighbors_datas,'indoor': node.indoor,'floor': node.floor}
        datas.append(data)

        #print("NodeController의 getAllNodes끝")

    sendData = {"Node": datas}

    resp = jsonify(sendData)

    return resp

#기존의 있는 노드에 자기만의 노드이름등록(검증완료)
@NodeController_index.route('/registerOwnNodeName', methods=['POST'])
def registerOwnNodeName():
    request_json = request.get_json()

    phone_origin_number_and_node = {}

    for firstkey, value in request_json.items():
        if firstkey == "phone_origin_number":
            phone_origin_number_and_node['phone_origin_number'] = value
        elif firstkey == "node_id":
            phone_origin_number_and_node['node_id'] = value
        elif firstkey == "own_node_name":
            phone_origin_number_and_node['own_node_name'] = value

    nodeServiceLogic.registerOwnNodeName(phone_origin_number_and_node)

    return 'success'

#새로운노드추가(검증완료)
@NodeController_index.route('/addNewNode', methods=['POST'])
def addNewNode():
    request_json = request.get_json()

    node_data = {}
    for firstkey, value in request_json.items():
        if firstkey == "node_type":
            node_data['node_type'] = value
        elif firstkey == "node_name":
            node_data['node_name'] = value
        elif firstkey == "pos_x":
            node_data['pos_x'] = value
        elif firstkey == "pos_y":
            node_data['pos_y'] = value
        elif firstkey == "node_neighbors":
            node_data['node_neighbors'] = value
        elif firstkey == "indoor":
            node_data['indoor'] = value
        elif firstkey == "floor":
            node_data['floor'] = value

    nodeServiceLogic.addNewNode(node_data)

    return 'success'

#노드수정(노드이름만수정)(검증완료)
@NodeController_index.route('/modifyNode', methods=['POST'])
def modifyNode():
    request_json = request.get_json()

    node_data={}

    for firstkey,value in request_json.items():
        if firstkey=="node_id":
            node_data['node_id']=value
        elif firstkey=="node_type":
            node_data['node_type']=value
        elif firstkey=="node_name":
            node_data['node_name']=value
        elif firstkey=="node_neighbors":
            node_data['node_neighbors']=value
        elif firstkey=="indoor":
            node_data['indoor']=value
        elif firstkey=="floor":
            node_data['floor']=value

    nodeServiceLogic.modifyNode(node_data)

    return 'success'

#노드삭제(노드 아이디가 하나만 온다고 가정,관리자만 가능)(검증완료)
@NodeController_index.route('/removeNode', methods=['POST'])
def removeNode():
    request_json = request.get_json()
    node_id=0

    for firstkey,value in request_json.items():
        node_id=value;

    nodeServiceLogic.removeNode(node_id)

    return 'success'


