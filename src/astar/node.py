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
    __slots__ = ('node_id', 'pos_x', 'pos_y', 'node_type', 'neighbors')

    def __init__(self, node_id, pos_x, pos_y, node_type=None, neighbors=None):
        if neighbors is None:
            neighbors = []
        self.node_id = node_id
        self.pos_x = pos_x
        self.pos_y = pos_y
        self.node_type = node_type
        self.neighbors = neighbors

    def set_neighbor(self, point, node):
        self.neighbors[point.value] = node

    def get_list(self):
        return self.neighbors


__all__ = ['Node', 'CardinalPoints']
