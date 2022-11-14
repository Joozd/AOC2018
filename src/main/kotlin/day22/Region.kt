package day22

import common.Coordinate

open class Region(coordinate: Coordinate, val erosionLevel: Int, protected val caveSystem: Cave):
    Coordinate(coordinate.x, coordinate.y)
{
    val dangerLevel get() = erosionLevel % 3

    fun withTool(tool: RegionWithTool.Tool) = RegionWithTool(Coordinate(x,y), erosionLevel, caveSystem, tool)

    fun terrain() = when(dangerLevel){
        0 -> "."
        1 -> "="
        2 -> "|"
        else -> error("ERROR")
    }
}