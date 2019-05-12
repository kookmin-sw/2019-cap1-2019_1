import os
import sys
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))

from StoreLogic.NodeStoreLogic import  NodeStoreLogic

nodeStoreLogic=NodeStoreLogic()

#import Node
from Domain.Node import Node

class NodeServiceLogic:

    def getAllNodes(self,identification_,phone_origin_number_):

        if identification_=="Administer":
            return nodeStoreLogic.selectAllNodes()
        elif identification_=="User":
            node_list=nodeStoreLogic.selectAllNodes()
            my_node_name_id_list=nodeStoreLogic.selectUserOwnNodeIdsByPhoneOriginNumber(phone_origin_number_)
            print(my_node_name_id_list)

            for my_node_id_and_own_node_name in my_node_name_id_list:
                for node_tmp in node_list:
                    if int(node_tmp.node_id)==my_node_id_and_own_node_name['node_id']:
                        node_tmp.node_name=my_node_id_and_own_node_name['own_node_name']

            return node_list

    #자기만의 노드이름등록
    def registerOwnNodeName(self, phone_origin_number_and_node):

        if len(nodeStoreLogic.selectOwnNodeNameByNodeId(phone_origin_number_and_node['node_id'],phone_origin_number_and_node['phone_origin_number']))!=0:
            return nodeStoreLogic.updateOwnNodeName(phone_origin_number_and_node['phone_origin_number']
                                                    , phone_origin_number_and_node['node_id']
                                                    , phone_origin_number_and_node['own_node_name'])
        else:
            return nodeStoreLogic.insertOwnNodeName(phone_origin_number_and_node['phone_origin_number']
                                                    , phone_origin_number_and_node['node_id']
                                                    , phone_origin_number_and_node['own_node_name'])

    #노드추가
    def addNewNode(self,node_data_):

        #먼저 새로운 노드 노드테이블에 넣음
        nodeStoreLogic.insertNewNode(node_data_)

        # 그다음 이웃노드 찾아서 새로운 들어온 노드아이디를 이웃노드컬럼에 추가해주어야함
        if node_data_['node_neighbors']!="":
            node_neighbors_list=node_data_['node_neighbors'].split("/")
            for to_add_neighbor_node_id in node_neighbors_list:
                node_tmp_node_neighbors=nodeStoreLogic.selectNodeByNodeId(to_add_neighbor_node_id)['node_neighbors']
                #maxNode가져와서 방금들어온 노드의 id를 알아내기
                max_node_id=str(nodeStoreLogic.selectMaxNodeId())
                changed_node_neigbhbors_list=nodeStoreLogic.split_and_modify_node_neighbors(max_node_id,node_tmp_node_neighbors,"Add")
                nodeStoreLogic.updateNodeNeighborsByNodeId(to_add_neighbor_node_id,changed_node_neigbhbors_list)

        return 'success'

    #노드수정
    def modifyNode(self,node_data_):
        #노드에 대한 이웃정보를 갖고와서 비교해서 분기정리
        current_node_neighbors_list=nodeStoreLogic.selectNodeByNodeId(node_data_["node_id"])['node_neighbors'].split("/")
        #들어온 node_data_의 node_neighbors id순서대로 오름차순정렬
        node_data_['node_neighbors']=self.sort_node_neighbor(node_data_['node_neighbors'])

        if node_data_['node_neighbors']!="":
            changed_node_neighbors_list=node_data_['node_neighbors'].split("/")
        else:
            changed_node_neighbors_list=[]

        print("serviceLogic modifyNode실행")
        print(current_node_neighbors_list)
        print(len(changed_node_neighbors_list))
        print(changed_node_neighbors_list)

        #노드의 이웃이 안바뀐경우
        if changed_node_neighbors_list==current_node_neighbors_list:
            nodeStoreLogic.updateNodeNeighborsNotChanged(node_data_)
        #노드의 이웃이 바뀐경우
        else:
            #제거할 이웃노드찾는 과정
            to_remove_neighbor_id_list = []
            for current_node_neighbor_id in current_node_neighbors_list:
                #기존 이웃노드가 바뀐 이웃노드 중에 없으면
                if current_node_neighbor_id not in changed_node_neighbors_list:
                    to_remove_neighbor_id_list.append((current_node_neighbor_id))

            #추가된 이웃노드찾는 과정
            to_add_neighbor_id_list=[]
            for changed_node_neighbors_id in changed_node_neighbors_list:
                if changed_node_neighbors_id not in current_node_neighbors_list:
                    to_add_neighbor_id_list.append((changed_node_neighbors_id))

            print(to_remove_neighbor_id_list)
            print(to_add_neighbor_id_list)

            #이웃노드 수정
            nodeStoreLogic.updateNodeNeighborsChanged(node_data_,to_remove_neighbor_id_list,to_add_neighbor_id_list)

        return 'success'

    #노드삭제
    def removeNode(self,node_id_):

        print("삭제할 노드_id:"+str(node_id_))

    #먼저 노드유저 테이블에 있는 노드 삭제
        nodeStoreLogic.deleteUserOwnNode(node_id_)
    #노드 테이블에 있는 노드중 이웃노드에서 삭제하고 다시 갱신
        node_id_list_and_neighbors=nodeStoreLogic.selectNodeIdsByNeighbor(node_id_)

        for pair in node_id_list_and_neighbors:
            neighbor_node_id=pair[0]
            node_neighbors=pair[1]
            modify_node_neighbors=self.split_node_neighbors(node_id_,node_neighbors)

            nodeStoreLogic.updateNodeNeighborsByNodeId(neighbor_node_id,modify_node_neighbors)
            print("node_neighbors갱신할 node_id:"+str(neighbor_node_id))
            print("갱신할 node_neighbors:"+str(modify_node_neighbors))

    #노드 테이블에서 노드 삭제
        nodeStoreLogic.deleteNode(node_id_)
        print('삭제완료')

        return 'success'

    #===========시스템 ServiceLogic함수=================

    #node_neighbors split하고 변경 해주는 함수
    def split_node_neighbors(self,node_id,node_neighbors_):

        split_node_neighbors_=node_neighbors_.split("/")
        #노드 이웃id중 삭제할 node_id제거
        print(node_id)
        print(split_node_neighbors_)
        split_node_neighbors_.remove(str(node_id))

        modify_node_neighbors=""

        for neighbor in split_node_neighbors_:
            modify_node_neighbors=modify_node_neighbors+neighbor+"/"

        #마지막 문자 제거
        modify_node_neighbors=modify_node_neighbors[:-1]
        print(modify_node_neighbors)

        return modify_node_neighbors

    #들어온 node_neighbor 정렬해서 string형태로 리턴하는 함수
    def sort_node_neighbor(self,node_neighbors_string_):

        modify_node_neighbors = ""

        if node_neighbors_string_ !="":

            split_node_neighbors_ = node_neighbors_string_.split("/")
            split_node_neighbors_.sort(key=float)



            for neighbor in split_node_neighbors_:
                modify_node_neighbors = modify_node_neighbors + neighbor + "/"

            # 마지막 문자 제거
            modify_node_neighbors = modify_node_neighbors[:-1]

        return modify_node_neighbors


