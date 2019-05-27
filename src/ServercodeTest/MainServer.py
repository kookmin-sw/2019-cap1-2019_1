import os
import sys

from flask import Flask
from Controller.NodeController import NodeController_index
from Controller.PathController import PathController_index
from Controller.UserController import UserController_index

app=Flask(__name__)

#여기에 Controller route를 추가해야함
app.register_blueprint(NodeController_index)
app.register_blueprint(PathController_index)
app.register_blueprint(UserController_index)

if __name__=='__main__':
	#app.run(host='0.0.0.0',port='5000',debug='True')
	app.run(debug='True')