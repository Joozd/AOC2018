package day11

import common.dijkstra.Coordinate
import common.Solution

class Day11: Solution {
    override val day = 11
    private val input = readInput().toInt()
    private lateinit var grid: Grid

    override fun answer1(): Coordinate {
        grid = Grid(filler)
        return grid.getBestCoordinate()
    }

    override fun answer2(): Coordinate {
        return grid.getBestCoordinateWithSize()
        /*
        var bestCoordinateSoFar: Grid.CoordinateWithPower = Grid.CoordinateWithPower(-1,-1, Int.MIN_VALUE)
        var bestSizeSofar: Int = -1
        (1..300).forEach{ size ->
            println("looking at $size")
            val bestCoordinateForThisSize = grid.getBestCoordinate(size)
            if (bestCoordinateForThisSize > bestCoordinateSoFar){
                bestCoordinateSoFar = bestCoordinateForThisSize
                bestSizeSofar = size
            }
        }
        return "${bestCoordinateSoFar.x},${bestCoordinateSoFar.y},$bestSizeSofar"

         */
    }

    private val filler: Grid.Filler = Grid.Filler { x, y ->
        //val id = x+10
        ((((x+10) * y) + input) * (x+10)) /100 %10 - 5
    }
}