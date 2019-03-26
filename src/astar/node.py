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


class Node:
    __slots__ = ('node_id', 'pos_x', 'pos_y', 'neighbors')

    def __init__(self, node_id, pos_x, pos_y, neighbors=None):
        if neighbors is None:
            neighbors = [None, None, None, None, None, None, None, None]
        self.node_id = node_id
        self.pos_x = pos_x
        self.pos_y = pos_y
        self.neighbors = neighbors

    def set_point(self, point, node):
        self.neighbors[point] = node

    def get_list(self):
        temp = []
        for p in self.neighbors:
            if p:
                temp.append(p)
        return temp
