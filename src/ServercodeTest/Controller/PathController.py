import os
import sys
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))

from flask import Blueprint
PathController_index=Blueprint('PathController_index',__name__)

from flask import jsonify
from flask import request

#import PathServiceLogic
from ServiceLogic.PathServiceLogic import PathServiceLogic
pathServiceLogic=PathServiceLogic()

#경로탐색(완료)
@PathController_index.route('/searchPath', methods=['POST'])
def searchPath():
    request_json = request.get_json()

    path_data = {}

    for firstkey, value in request_json.items():
        if firstkey == "identification":
            path_data['identification'] = value
        elif firstkey == "phone_origin_number":
            path_data['phone_origin_number'] = value
        elif firstkey == "departure_node_id":
            path_data['departure_node_id'] = value
        elif firstkey == "arrival_node_id":
            path_data['arrival_node_id'] = value

    # identification="User"
    # phone_origin_number="0xx0"
    # departure_node_id=1
    # arrival_node_id=6

    path_node_id_list=pathServiceLogic.searchPath(path_data['identification'],path_data['phone_origin_number'],path_data['departure_node_id'],path_data['arrival_node_id'])

    datas=[]

    for path_node in path_node_id_list:
        datas.append(path_node.node_id)

    paths = {"Path":datas}
    resp=jsonify(paths)

    return resp