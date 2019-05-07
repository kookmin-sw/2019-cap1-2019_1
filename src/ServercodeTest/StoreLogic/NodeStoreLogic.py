import os
import sys
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))

from Database.DatabaseInstance import db
from Database.DatabaseInstance import con
from Domain.Node import Node

class NodeStoreLogic:

    def selectAllNodes(self):

        db.execute('SELECT node_id,node_type,node_name,pos_x,pos_y,node_neighbors,indoor,floor FROM NODE_TB  ORDER BY NODE_ID ASC')
        records = db.fetchall()

        print(records)

        node_and_node_neighbors_list=[]

        for record in records:

            #print(record)

            #리스트 뽑은거 리스트 형태로 Node생성자에 추가
            node=Node(record[0],record[2],record[3],record[4],record[6],record[7],record[1])
            tmp=(node,record[5])
            node_and_node_neighbors_list.append(tmp)

        # neigbor노드에 대한 노드 리스트에 추가
        node_list=[]
        for node_and_node_neighbors in node_and_node_neighbors_list:
            node_tmp=node_and_node_neighbors[0]
            data_node_neighbors_list=node_and_node_neighbors[1].split("/")

            #data_node_neighbors_list를 for문돌면서 각 id에 해당하는 Node를 node_list_의 객체에 넣어줌
            for neighbors_id in data_node_neighbors_list:
                for node_and_node_neighbors_tmp in node_and_node_neighbors_list:
                    if str(node_and_node_neighbors_tmp[0].node_id)==neighbors_id:
                        node_tmp.set_neighbor(node_and_node_neighbors_tmp[0])

            node_list.append(node_tmp)


        #node_list 데이터 디버깅용
        # for node in node_list:
        #     print("!~!~")
        #     print(node)
        #     print(node.node_id)
        #     print("!!neighbors start")
        #     for tmp in node.neighbors:
        #
        #         print(tmp.node_id)
        #         #print(tmp.neighbors)
        #
        #     print("!!neighbors end")


        return node_list

    def selectNodeByNodeId(self,node_id_):
        print("NodeStoreLogic의 selectNodeByNodeId시작")
        print(node_id_)
        sql="SELECT node_id,node_type,node_name,pos_x,pos_y,node_neighbors,indoor,floor FROM NODE_TB WHERE NODE_ID= :node_id"

        db.execute(sql,{"node_id":node_id_})
        records = db.fetchall()

        node_list_and_node_neighbors = []

        for record in records:
            # print(record)

            node_data={'node_id': record[0], 'node_type': record[1], 'node_name': record[2],
             'pos_x':record[3],'pos_y':record[4],'node_neighbors':record[5], 'indoor': record[6], 'floor': record[7]}

            print(node_data)

        return node_data


    #자기만의 노드이름등록
    def insertOwnNodeName(self,phone_origin_number_,node_id_,own_node_name_):

        sql="INSERT INTO NODE_USER_TB(phone_origin_number, node_id, own_node_name)" + \
            "values (:phone_origin_number, :node_id, :own_node_name)"
        datas={'phone_origin_number':phone_origin_number_,'node_id':node_id_,'own_node_name':own_node_name_}

        db.execute(sql,{'phone_origin_number':phone_origin_number_,'node_id':node_id_,'own_node_name':own_node_name_})
        con.commit()

        return 'success'

    #노드추가(노드 이름 등록해도 되고 안해도되고)
    def insertNewNode(self,node_list_):

        # for node in node_list_:

            #data=[node.node_type,node.node_name,node.pos_x,node.pos_y,node.neighbors,node.indoor,node.floor]


            sql = "insert into NODE_TB(node_id,node_type,node_name,pos_x,pos_y,node_neighbors,indoor,floor)" + \
                  "values ((SELECT NVL(MAX(NODE_ID),0)+1 FROM NODE_TB),:node_type,:node_name,:pos_x,:pos_y,:node_neighbors,:indoor,:floor)"

            db.execute(sql, node_list_)

            con.commit()
        # , node_name, pos_x, pos_y, node_neighbors, indoor, floor
            return 'success'

    #노드수정(이웃노드 안바꼈을경우)
    def updateNodeNeighborsNotChanged(self,node_data):

        sql = "UPDATE NODE_TB SET NODE_ID= :node_id,NODE_TYPE= :node_type,NODE_NAME= :node_name,NODE_NEIGHBORS = :node_neighbors,INDOOR= :indoor,FLOOR= :floor WHERE NODE_ID= :node_id"
        db.execute(sql, {'node_id':node_data['node_id'],'node_type':node_data['node_type'],'node_name':node_data['node_name'],'node_neighbors':node_data['node_neighbors'],'indoor':node_data['indoor'],'floor':node_data['floor']})
        con.commit()

        return 'success'
    #노드수정(이웃노드 바꼈을경우)
    def updateNodeNeighborsChanged(self, node_data_, to_remove_neighbor_id_list_, to_add_neighbor_id_list_):

        #일단 바뀐 노드 변경
        sql = "UPDATE NODE_TB SET NODE_ID= :node_id,NODE_TYPE= :node_type,NODE_NAME= :node_name,NODE_NEIGHBORS = :node_neighbors,INDOOR= :indoor,FLOOR= :floor WHERE NODE_ID= :node_id"
        db.execute(sql, {'node_id': node_data_['node_id'], 'node_type': node_data_['node_type'],
                         'node_name': node_data_['node_name'], 'node_neighbors': node_data_['node_neighbors'],
                         'indoor': node_data_['indoor'], 'floor': node_data_['floor']})


        if len(to_remove_neighbor_id_list_)!=0:
            #이웃노드 제거된거 이 이웃노드 찾아가서 이웃제거
            print("NodeStoreLogic의 이웃노드 제거된거 이웃제거하기")
            for to_remove_neighbor_id in to_remove_neighbor_id_list_:
                node_neighbors_string=self.selectNodeByNodeId(to_remove_neighbor_id)['node_neighbors']

                changed_neighbors_list_string=self.split_and_modify_node_neighbors(str(node_data_['node_id']), node_neighbors_string, "Remove")
                self.updateNodeNeighborsByNodeId(to_remove_neighbor_id,changed_neighbors_list_string)

        if len(to_add_neighbor_id_list_)!=0:
        #이웃노드 추가된거 이웃노드 찾아가서 이웃추가.
            print("NodeStoreLogic의 이웃노드 추가된거 이웃추가하기")
            for to_add_neighbor_id in to_add_neighbor_id_list_:
                node_neighbors_string=self.selectNodeByNodeId(int(to_add_neighbor_id))['node_neighbors']
                print("NodeStoreLogic의 add부분시작")
                print(to_add_neighbor_id_list_)
                print(node_neighbors_string)
                print("NodeStoreLogic의 add부분끝")

                changed_neighbors_list_string = self.split_and_modify_node_neighbors(str(node_data_['node_id']), node_neighbors_string, "Add")
                self.updateNodeNeighborsByNodeId(to_add_neighbor_id, changed_neighbors_list_string)

        con.commit()

    #노드삭제
    def deleteNode(self,node_id_):

        sql = "delete from node_tb where node_id=:id"
        db.execute(sql, {'id': node_id_})
        con.commit();

        return 'success'

    # ======노드유저테이블==========
    def selectUserOwnNodeIdsByPhoneOriginNumber(self,phone_origin_number_):
        sql = "SELECT node_id, own_node_name FROM NODE_USER_TB WHERE PHONE_ORIGIN_NUMBER=:phone_origin_number"
        db.execute(sql, {'phone_origin_number': phone_origin_number_})

        records = db.fetchall()
        node_id_list = []

        print(records)

        for record in records:
            # 인접노드에 대한 것 리스트로 뽑아오기
            node_id_and_own_node_name = {}
            node_id_and_own_node_name['node_id']=record[0]
            node_id_and_own_node_name['own_node_name']=record[1]

            node_id_list.append(node_id_and_own_node_name)

        return node_id_list

    def deleteUserOwnNode(self, node_id_):
        sql = "delete from node_user_tb where node_id=:id"
        db.execute(sql, {'id': node_id_})
        con.commit();

        return 'success'

    # ======시스템 StoreLogic========
    def selectNodeIdsByNeighbor(self, neighbor_node_id_):
        sql = "SELECT node_id,node_neighbors FROM node_tb WHERE node_neighbors LIKE '%' || :neighbor_node_id || '%'"

        db.execute(sql, {'neighbor_node_id': neighbor_node_id_})
        records = db.fetchall()
        node_id_list = []

        for record in records:
            # 인접노드에 대한 것 리스트로 뽑아오기

            node_id_list.append(record)

        return node_id_list

    def selectMaxNodeId(self):

        sql = "SELECT NVL(MAX(NODE_ID), 0) FROM  NODE_TB"

        db.execute(sql)
        record = db.fetchone()

        print("NodeStoreLogic의 selectMaxNodeId시작")
        print(record)
        print("NodeStoreLogic의 selectMaxNodeId끝")
        return record[0]

    def updateNodeNeighborsByNodeId(self, node_id_, node_neighbors_):
        sql = "UPDATE NODE_TB SET NODE_NEIGHBORS = :node_neighbors WHERE NODE_ID= :node_id"
        db.execute(sql, {'node_id': node_id_, 'node_neighbors': node_neighbors_})
        con.commit()

        return 'success'

    # node_neighbors split하고 그 리스트에서 제거할 이웃노드 제거 해주고 String으로 return하는 함수
    def split_and_modify_node_neighbors(self, node_id, node_neighbors_,case_type):

        #여기서 에러 해결됨
        if node_neighbors_!=None:
            split_node_neighbors_ = node_neighbors_.split("/")
        elif node_neighbors_==None:
            split_node_neighbors_=[]

        # 노드 이웃id중 삭제할 node_id제거
        if(case_type=="Remove"):
            print("Remove함수실행")
            print(node_id)
            print(split_node_neighbors_)
            split_node_neighbors_.remove(node_id)
        #이웃에 node_id추가
        elif(case_type=="Add"):
            print("Add함수실행")
            print(node_id)
            print(split_node_neighbors_)
            split_node_neighbors_.append(node_id)
            #이웃노드 들어갈때 이웃노드id 정렬해주는것
            split_node_neighbors_.sort(key=float)

        modify_node_neighbors = ""

        for neighbor in split_node_neighbors_:
            modify_node_neighbors = modify_node_neighbors + neighbor + "/"

        # 마지막 문자 제거
        modify_node_neighbors = modify_node_neighbors[:-1]

        return modify_node_neighbors




