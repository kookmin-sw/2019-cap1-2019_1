from node import Node
import astar

nodeA = Node(1, 0.0, 0.0)
nodeB = Node(2, 0.0, 1.0)
nodeC = Node(3, 0.5, 0.0)
nodeD = Node(4, 0.5, 1.5)

nodeA.set_neighbor(nodeB)
nodeB.set_neighbor(nodeA)
nodeA.set_neighbor(nodeC)
nodeC.set_neighbor(nodeA)
nodeD.set_neighbor(nodeB)
nodeB.set_neighbor(nodeD)
nodeD.set_neighbor(nodeC)
nodeC.set_neighbor(nodeD)

graph = astar.AStar()
path = graph.astar(nodeA, nodeD)

for n in path:
    print(n.node_id)