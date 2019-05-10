import os
import sys
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))

from flask import jsonify
from flask import Blueprint
UserController_index=Blueprint('UserController_index',__name__)

from flask import jsonify
from flask import request

from ServiceLogic.UserServiceLogic import UserServiceLogic
userServiceLogic=UserServiceLogic()

#사용자등록
@UserController_index.route('/registerUser', methods=['POST'])
def registerUser():

    request_json = request.get_json()
    user_data = {}

    for firstkey, value in request_json.items():
        if firstkey == "phone_origin_number":
            user_data['phone_origin_number'] = value

    userServiceLogic.registerUser(user_data)

    return 'success'