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
    __slots__ = ('N', 'NE', 'E', 'SE', 'S', 'SW', 'W', 'NW')

    def __init__(self):
        self.N = None
        self.NE = None
        self.E = None
        self.SE = None
        self.S = None
        self.SW = None
        self.W = None
        self.NW = None


class Node:
    __slots__ = ('node_id', 'pos_x', 'pos_y', 'neighbors')

    def __init__(self, node_id, pos_x, pos_y, neighbors=Neighbors()):
        self.node_id = node_id
        self.pos_x = pos_x
        self.pos_y = pos_y
        self.neighbors = neighbors
