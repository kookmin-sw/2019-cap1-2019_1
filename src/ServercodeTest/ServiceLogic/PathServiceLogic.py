import os
import sys
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))

from ServiceLogic.NodeServiceLogic import NodeServiceLogic
from Astar.Astar import AStar
from Domain.Node import Node

nodeServiceLogic=NodeServiceLogic()
graph=AStar()

class PathServiceLogic:
    def searchPath(self, identification_, phone_origin_number_, departure_node_id_, arrival_node_id_):

        node_list=nodeServiceLogic.getAllNodes(identification_,phone_origin_number_)

        departure_node=None
        arrival_node=None

        #debug
        # for node_tt in node_list:
        #     print("node시작")
        #     print(node_tt.node_id)
        #     print(node_tt.pos_x)
        #     print(node_tt.pos_y)
        #     for node_nei in node_tt.neighbors:
        #         print("node이웃시작")
        #         print(node_nei.node_id)
        #         print("node이웃끝")
        #     print("node끝")
        #==

        for node_tmp in node_list:
            if node_tmp.node_id==departure_node_id_:
                departure_node=node_tmp
            elif node_tmp.node_id==arrival_node_id_:
                arrival_node=node_tmp

        if departure_node==None or arrival_node==None:
            return 'fail'
        else:
            path = graph.astar(departure_node, arrival_node)
            return path



