

from flask import Flask
from flask import jsonify
from flask import json


#Oracle
import cx_Oracle

#json request
from flask import request

#한국어 깨짐문제
from functools import wraps

app=Flask(__name__)

#DB connect
con=cx_Oracle.connect('scott/12345678@oracle11gr2.cdubaygxhxxb.ap-northeast-2.rds.amazonaws.com:1521/orcl')
db=con.cursor()


#한국어 깨짐 문제
def as_json(f):
	@wraps(f)
	def decorated_function(*args,**kwargs):
		res=f(*args,**kwargs)
		res=json.dumps(res,ensure_ascii=False).encode('utf8')
		return Response(res,content_type='application/json;charset=utf-8')
	return decorated_function


#class
class Node(object):
	def __init__(self,name,direction,id):
		self.name=name
		self.direction=direction
		self.id=id


#route
@app.route('/')
def hello():
	return 'Welcome'

@app.route('/json')
def json_test():
	data={'hello' : 'world', 'number':3}
	resp=jsonify(data)	
	return resp

@app.route('/select',methods=['POST'])
def select():
	#con=cx_Oracle.connect('scott/12345678@oracle11gr2.cdubaygxhxxb.ap-northeast-2.rds.amazonaws.com:1521/orcl')
	#db=con.cursor()

	db.execute('SELECT * FROM NODE')
	records=db.fetchall()
	datas=[]
	data={}
	for record in records:
		print(record)
		data={'node_id':record[0],'node_type':record[1],'node_name':record[2],'pos_x':record[3],'pos_y':record[4],'node_neighbors':record[5]}	
		datas.append(data)
		data={}

	sendData={"Node":datas}		
		

	resp=jsonify(sendData)

	return resp

#Insert example
@app.route('/insert', methods=['POST'])
def insert():
	
	print("call")
	#print("안녕")	
	#print(request.headers['content-type'])
	#print(request.data)
	request_json=request.get_json()
	#print("after requestjson")
		

	datas=[]
	data=()

	for firstkey, big_list in request_json.items():
		
		if firstkey=="Node":
			for pair in big_list:
				#print("no")
				data=(pair["node_id"],pair["node_type"],pair["node_name"],pair["pos_x"],pair["pos_y"],pair["node_neighbors"])
				#print("son")
				datas.append(data)
				data=()
	
	db.bindarraysize=2
	#double형이여도 int로 써도됨
	db.setinputsizes(int,20,20,int,int,20)
	db.executemany("insert into node(node_id,node_type,node_name,pos_x,pos_y,node_neighbors) values (:1,:2,:3,:4,:5,:6)",datas)
	con.commit()
	
	#db.close()
	#con.close()

	return 'success'

@app.route('/update')
def update():
	
	
	return 'success'

if __name__=='__main__':
	app.run(host='0.0.0.0',port='5000',debug='True')