package day11

import common.dijkstra.Coordinate

class Grid(private val filler: Filler) {
    private val grid = Array(300){ y ->
        IntArray(300){ x ->
            filler.getLevel(x, y)
        }
    }

    fun getSquareValue(x: Int, y: Int, squareSize: Int): Int =
        (x until x + squareSize).sumOf { xx ->
            (y until y + squareSize).sumOf { yy ->
                get(xx, yy)
            }
        }

    fun getBestCoordinate(size: Int = 3): CoordinateWithPower{
        var currentBestCoordinate = CoordinateWithPower(-1,-1, Int.MIN_VALUE)
        (0 until 300-size).forEach{ x ->
            (0 until 300-size).forEach{ y ->
                val power = getSquareValue(x, y, size)
                if (power > currentBestCoordinate.power) {
                    currentBestCoordinate = CoordinateWithPower(x, y, power)
                }
            }
        }

        return currentBestCoordinate
    }

    fun getBestCoordinateWithSize(): CoordinateWithPowerAndsize{
        var currentBestCoordinate = CoordinateWithPowerAndsize(-1,-1, Int.MIN_VALUE, -1)
        (0 until 300).forEach{ x ->
            (0 until 300).forEach{ y ->
                val current = getBestSizeForCoordinate(x, y)
                if (current > currentBestCoordinate) {
                    currentBestCoordinate = current
                }
            }
        }
        return currentBestCoordinate
    }

    private fun getBestSizeForCoordinate(x: Int, y: Int): CoordinateWithPowerAndsize{
        val maxSize = minOf(300-x, 300-y)
        var bestPower: Int = Int.MIN_VALUE
        var previousPower = 0
        var bestSize = 0
        (0 until maxSize).forEach{ size ->
            val extraXPower = (x .. x+size).sumOf { xx -> get (xx, y+size) }
            val extraYPower = (y until y+size).sumOf { yy -> get (x+size, yy) }
            val power = previousPower + extraXPower + extraYPower
            if (power > bestPower){
                bestPower = power
                bestSize = size
            }
            previousPower = power
        }
        return CoordinateWithPowerAndsize(x, y, bestPower, bestSize +1) // size +1 as 0 makes a 1x1 grid
    }

    fun get(x: Int, y: Int): Int = grid[y][x]


    fun interface Filler{
        fun getLevel(x: Int, y: Int): Int // calculating on the fly takes 3x as long
    }

    open class CoordinateWithPower(x: Int, y: Int, val power: Int): Coordinate(x,y){
        override fun toString() = "Coordinate ($x, $y) with power $power"

        operator fun compareTo(other: CoordinateWithPower) = power.compareTo(other.power)
    }

    class CoordinateWithPowerAndsize(x: Int, y: Int, power: Int, val size: Int): CoordinateWithPower(x,y,power){
        override fun toString() = "Coordinate ($x, $y) with power $power and size $size"
    }
}