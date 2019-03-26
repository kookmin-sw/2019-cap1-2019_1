class Node:
    __slots__ = ('node_id', 'pos_x', 'pos_y', 'neighbors')

    def __init__(self, node_id, pos_x, pos_y, neighbors):
        self.node_id = node_id
        self.pos_x = pos_x
        self.pos_y = pos_y
        self.neighbors = neighbors


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
