package day22

import common.Coordinate
import common.dijkstra.AStar
import common.dijkstra.Node
import kotlin.math.absoluteValue

class RegionWithTool(coordinate: Coordinate, erosionLevel: Int, caveSystem: Cave, val tool: Tool):
    Region(coordinate, erosionLevel, caveSystem), AStar.AStarNode
{
    override fun equals(other: Any?): Boolean = when {
        other == null || other !is Coordinate -> false
        other !is RegionWithTool -> other.x == x && other.y == y // other is Region but does not have a tool so we only compare x and y
        else -> other.x == x && other.y == y && other.tool == tool
    }

    override fun hashCode(): Int =
        super.hashCode() + tool.hashCode()

    /**
     * Get a list of all Nodes that can be reached from this node without passing any other node.
     * In this case: those that can be reached with this tool, or this with another tool.
     */
    override fun getNeighbours(): List<AStar.AStarNode> =
        adjacentCoordinates
            .filter { it.x >= 0 && it.y >= 0}
            .map {
                caveSystem[it]
            }.filter {
                isAccessible(it)
            }.map { it.withTool(tool)} +
                thisWithOtherTools()

    override fun minimumDistanceTo(target: Node<Int>): Int {
        require(target is Region)
        return (target.x-x).absoluteValue + (target.y - y).absoluteValue
    }

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

    fun withChangedTool(): RegionWithTool{
        val newTool = when(dangerLevel){
            DangerLevel.WET -> if (tool == Tool.CLIMBING_GEAR) Tool.NEITHER else Tool.CLIMBING_GEAR
            DangerLevel.NARROW -> if (tool == Tool.TORCH) Tool.NEITHER else Tool.TORCH
            DangerLevel.ROCKY -> if(tool == Tool.CLIMBING_GEAR) Tool.TORCH else Tool.CLIMBING_GEAR
            else -> error("Error 17b")
        }
        return this.withTool(newTool)
    }

    private fun hasSameCoordinates(neighbour: RegionWithTool) =
        neighbour.x == x && neighbour.y == y

    enum class Tool{
        TORCH,
        CLIMBING_GEAR,
        NEITHER
    }


}
