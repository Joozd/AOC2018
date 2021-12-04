package day15

import common.Coordinate
import common.CoordinateNode
import common.CoordinateWithChar
import common.dijkstra.GridNode
import common.dijkstra.Node

class Position(x: Int, y: Int, private val neighbours: List<CoordinateNode>): CoordinateWithChar(x,y, '.'), GridNode {
    override val c get() = generateChar()

    var occupiedBy: Creature? = null

    override fun getNeighbours() = neighbours // Needs to be filtered to check if occupied!

    override fun getDistanceToNeighbour(neighbour: Node<Int>) = 1

    private fun generateChar() = when (occupiedBy){
        is Elf -> 'E'
        is Goblin -> 'G'
        else -> '.'
    }


    companion object{
        fun make(coordinate: CoordinateWithChar, allCoordinates: List<CoordinateWithChar>): Position{
            val neighbours = findNeighbours(coordinate, allCoordinates)
            return Position(coordinate.x, coordinate.y, neighbours.sorted())
        }

        private fun findNeighbours(coordinate: CoordinateWithChar, allCoordinates: List<CoordinateWithChar>): List<CoordinateNode> =
            listOfNotNull(
                allCoordinates.firstOrNull { it.x == coordinate.x && it.y == coordinate.y-1 }, // above
                allCoordinates.firstOrNull { it.x == coordinate.x-1 && it.y == coordinate.y }, // left
                allCoordinates.firstOrNull { it.x == coordinate.x+1 && it.y == coordinate.y }, // right
                allCoordinates.firstOrNull { it.x == coordinate.x && it.y == coordinate.y+1 }  // down
            )
                .map { CoordinateNode(it) }
    }
}


