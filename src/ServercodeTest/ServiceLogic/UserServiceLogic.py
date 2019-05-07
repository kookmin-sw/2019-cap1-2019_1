import os
import sys
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))

#import UserStoreLogic
from StoreLogic.UserStoreLogic import UserStoreLogic

userStoreLogic=UserStoreLogic()

class UserServiceLogic:
    def registerUser(self,user_data_):

        userStoreLogic.registerUser(user_data_)

        return 'success'