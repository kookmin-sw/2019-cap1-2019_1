import enum


class CardinalPoints(enum.Enum):
    N = 0
    NE = 1
    E = 2
    SE = 3
    S = 4
    SW = 5
    W = 6
    NW = 7


class Neighbors:
    __slots__ = 'neighbor_list'

    def __init__(self):
        self.neighbor_list = [None, None, None, None, None, None, None, None]



class Node:
    __slots__ = ('node_id', 'pos_x', 'pos_y', 'neighbors')

    def __init__(self, node_id, pos_x, pos_y, neighbors=Neighbors()):
        self.node_id = node_id
        self.pos_x = pos_x
        self.pos_y = pos_y
        self.neighbors = neighbors
