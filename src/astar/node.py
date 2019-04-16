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

    def set_neighbor(self, node):
        self.neighbors.append(node)

    def get_list(self):
        return self.neighbors


__all__ = ['Node']
