import cx_Oracle

from Database.DatabaseConfig import DB_ID
from Database.DatabaseConfig import DB_URL
from Database.DatabaseConfig import DB_NID

#DB connect
#서버에서 DB로 한글데이터 넘어갈때 encoding,nencoding  'UTF-8'로 설정해주어야함
con=cx_Oracle.connect(DB_ID+'/'+DB_URL+'/'+DB_NID,encoding='UTF-8', nencoding='UTF-8')
db=con.cursor()

