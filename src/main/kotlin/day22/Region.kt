package day22

import common.Coordinate
import common.dijkstra.GridNode
import common.dijkstra.Node

class Region(coordinate: Coordinate, val erosionLevel: Int, private val caveSystem: Cave):
    Coordinate(coordinate.x, coordinate.y), GridNode
{
    val dangerLevel get() = erosionLevel % 3

    /**
     * Get a list of all Nodes that can be reached from this node without passing any other node
     */
    override fun getNeighbours(): List<Node<Int>> = adjacentCoordinates.map{
        caveSystem[it]
    }

    /**
     * Get the distance to a neighbour
     */
    override fun getDistanceToNeighbour(neighbour: Node<Int>): Int {
        TODO("Not implemented")
    }
}