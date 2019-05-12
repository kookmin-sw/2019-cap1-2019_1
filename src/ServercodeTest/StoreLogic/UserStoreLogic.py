import os
import sys
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))

from Database.DatabaseInstance import db
from Database.DatabaseInstance import con
from Domain.Node import Node

class UserStoreLogic:
    def registerUser(self,user_data_):

        sql = "insert into USER_TB(user_id,phone_origin_number)" + \
              "values ((SELECT NVL(MAX(USER_ID),0)+1 FROM USER_TB),:phone_origin_number)"
        db.execute(sql, user_data_)
        con.commit()

        return 'success'

    def selectUserIdByPhoneOriginNumber(self,phone_origin_number_):

        sql = "SELECT user_id FROM USER_TB WHERE PHONE_ORIGIN_NUMBER= :phone_origin_number"
        db.execute(sql, {"phone_origin_number": phone_origin_number_})
        records = db.fetchall()
        return records