from flask import Flask
from flask import jsonify
from flask import json

#Oracle
import cx_Oracle

app=Flask(__name__)

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

@app.route('/dbtest')
def db_test():
	con=cx_Oracle.connect('scott/12345678@oracle11gr2.cdubaygxhxxb.ap-northeast-2.rds.amazonaws.com:1521/orcl')
	db=con.cursor()

	db.execute('SELECT * FROM NODE')

	#global dataAll

	#first case to retrieve db data
	for record in db:
		#print(record)
		#print(record[0])
		#print(record[1])
		
		#make json
		data={'name':record[0],'direction':record[1],'id':record[2]}
		
		dataAll=json.dumps(data)
		
		

		resp=jsonify(data)
		#aa=record
	#print (aa)

	#testing
	#for record in db:
		#tmp=json.dums(record)	

	#second case to retrieve db data(using object)
	#data={}
	

	#for record in db:
	
		#node=Node(record[0],record[1],record[2])	
		
		#dataAll=json.dumps(node.__dict__)
		#data.append(dataAll)
		#print(dataAll)


	

	db.close()
	con.close()	
	
	#dataFinal=json.dumps({'Nodes':data})

	#print(dataFinal)

	return resp

if __name__=='__main__':
	app.run(host='0.0.0.0',port='5000',debug='True')
