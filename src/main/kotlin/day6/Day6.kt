package day6

import common.Coordinate
import common.Solution

class Day6: Solution {
    override val day = 6
    private val input = inputLines()
    private lateinit var coordinates: List<Coordinate>
    private lateinit var grid: Array<Array<GridLocation>>

    override fun answer1(): Int? {
        fillMap()
        val minX = coordinates.minOf { it.x }
        val minY = coordinates.minOf { it.y }
        val maxX = coordinates.maxOf { it.x }
        val maxY = coordinates.maxOf { it.y }

        val hSize = maxX - minX + 1
        val vSize = maxY - minY + 1

        //Grid is a 300-ish by 300-ish square Array of GridLocations

        // We only have the interesting part of the grid.
        grid = Array(hSize){ x -> Array(vSize) { y -> GridLocation(x + minX, y + minY, coordinates)} }

        val filledGrid = grid.map{ col ->
            col.map{ gl ->
                gl.closest()
            }
        }

        val infiniteAreas = (filledGrid[0] +
                filledGrid.last() +
                filledGrid.map {it[0]} +
                filledGrid.map {it.last()})
            .toSet()

        val interestingAreas = coordinates.filter { it !in infiniteAreas}

        val sizes = interestingAreas.map{ c ->
            grid.flatten().count {
                it.closest() == c }
        }

        println("sizes: $sizes")

        return sizes.maxOrNull()
    }

    override fun answer2(): Int =
        grid.sumOf { col -> col.count { gl -> gl.totalDistance() < 10000 } }


    private fun fillMap(){
        coordinates = input.map { s ->
            s.split(", ")
                .map{it.toInt()}
                .let{ Coordinate(it[0], it[1])}
        }

    }
}