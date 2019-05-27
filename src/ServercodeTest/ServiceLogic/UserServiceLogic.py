import os
import sys
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))

#import UserStoreLogic
from StoreLogic.UserStoreLogic import UserStoreLogic

userStoreLogic=UserStoreLogic()

class UserServiceLogic:
    def registerUser(self,user_data_):

        if(len(userStoreLogic.selectUserIdByPhoneOriginNumber(user_data_['phone_origin_number']))!=0):
            return 'User Already Exits'
        else:
            userStoreLogic.registerUser(user_data_)
            return 'success'
