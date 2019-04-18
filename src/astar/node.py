class Node:
    __slots__ = ('node_id', 'node_name', 'pos_x', 'pos_y', 'indoor', 'floor', 'node_type', 'neighbors')

    def __init__(self, node_id, node_name, pos_x, pos_y, indoor=False, floor=0, node_type=None, neighbors=None):
        if neighbors is None:
            neighbors = []
        self.node_id = node_id
        self.node_name = node_name
        self.pos_x = pos_x
        self.pos_y = pos_y
        self.indoor = indoor
        self.floor = floor
        self.node_type = node_type
        self.neighbors = neighbors

    def set_neighbor(self, node):
        self.neighbors.append(node)

    def get_list(self):
        return self.neighbors


__all__ = ['Node']
