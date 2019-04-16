from node import Node, CardinalPoints
import astar

nodeA = Node(1, 0.0, 0.0)
nodeB = Node(2, 0.0, 1.0)
nodeC = Node(3, 0.5, 0.0)
nodeD = Node(4, 0.5, 1.5)

nodeA.set_neighbor(CardinalPoints.N, nodeB)
nodeB.set_neighbor(CardinalPoints.S, nodeA)
nodeA.set_neighbor(CardinalPoints.E, nodeC)
nodeC.set_neighbor(CardinalPoints.W, nodeA)
nodeD.set_neighbor(CardinalPoints.NW, nodeB)
nodeB.set_neighbor(CardinalPoints.SE, nodeD)
nodeD.set_neighbor(CardinalPoints.S, nodeC)
nodeC.set_neighbor(CardinalPoints.N, nodeD)

graph = astar.AStar()
path = graph.astar(nodeA, nodeD)

for n in path:
    print(n.node_id)