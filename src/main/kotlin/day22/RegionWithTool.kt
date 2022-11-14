package day22

import common.Coordinate
import common.dijkstra.Node

class RegionWithTool(coordinate: Coordinate, erosionLevel: Int, caveSystem: Cave, val tool: Tool):
    Region(coordinate, erosionLevel, caveSystem), Node<Int>
{
    override fun equals(other: Any?): Boolean =
        if (other == null || other !is RegionWithTool)
            false
        else
            other.x == x && other.y == y && other.tool == tool

    override fun hashCode(): Int =
        super.hashCode() + tool.hashCode()

    /**
     * Get a list of all Nodes that can be reached from this node without passing any other node.
     * In this case: those that can be reached with this tool, or this with another tool.
     */
    override fun getNeighbours(): List<Node<Int>> =
        adjacentCoordinates
            .filter { it.x in (0..100) && it.y >= 0} // 1023 was too high, so can't go more than 150 out of the way. Some tool changing involved as well - lets make it 100
            .map {
                caveSystem[it]
            }.filter {
                isAccessible(it)
            }.map { it.withTool(tool)} +
                thisWithOtherTools()

        /**
         * Get the distance to a neighbour
         */
        override fun getDistanceToNeighbour(neighbour: Node<Int>): Int =
            if (hasSameCoordinates(neighbour as RegionWithTool)) 7 else 1

    private fun isAccessible(neighbour: Region): Boolean = when(neighbour.dangerLevel){
        DangerLevel.WET -> tool != Tool.TORCH
        DangerLevel.NARROW -> tool != Tool.CLIMBING_GEAR
        DangerLevel.ROCKY -> tool != Tool.NEITHER
        else -> error("Error 17a")
    }

    private fun thisWithOtherTools() = Tool.values().filter{ it != tool}.map{
        this.withTool(it)
    }

    private fun hasSameCoordinates(neighbour: RegionWithTool) =
        neighbour.x == x && neighbour.y == y

    enum class Tool{
        TORCH,
        CLIMBING_GEAR,
        NEITHER
    }


}
