package common

import common.dijkstra.Node

/**
 * Only used to find a coordinate, not for actual pathfinding!
 */
class CoordinateNode(x: Int, y: Int): Coordinate(x,y), Node<Int> {
    constructor(c: Coordinate): this(c.x, c.y)

    override fun getNeighbours(): List<Node<Int>> {
        error ("Stub")
    }

    override fun getDistanceToNeighbour(neighbour: Node<Int>): Int {
        error ("Stub")
    }

}