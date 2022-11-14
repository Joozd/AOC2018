package day22

import common.Coordinate
import common.Solution
import common.dijkstra.Dijkstra
import common.extensions.grabInts

class Day22: Solution {
    override val day = 22
    private val depth = inputLines().first().grabInts().first() // 510 //
    private val target = inputLines().last().grabInts().let { Coordinate(it[0], it[1]) } // Coordinate(10,10) // Coordinate(10,10) //

    private val cave = Cave(depth, target)

    override fun answer1() = with(cave) {
        // this will populate the cave up to target -(1,1). As target is already prepopulated, we not have a populated square due to how the cave gets calculated.
        get(Coordinate(target.x - 1, target.y))
        get(Coordinate(target.x, target.y - 1))
        riskLevel()
    }

    // 1023 too high 
    override fun answer2(): Any {
        val start = cave[Coordinate(0, 0)].withTool(RegionWithTool.Tool.TORCH)
        val end = cave[target].withTool(RegionWithTool.Tool.TORCH)
        return Dijkstra(start, end).distance()
    }
}