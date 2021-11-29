package day13

import common.Coordinate
import common.extensions.increaseWithinBounds

class Cart(startPos: Track, direction: Char) {
    var currentPos: Track = startPos

    private var alive = true

    fun died(){
        alive = false
    }

    private var nextIntersection: Int = LEFT

    override fun toString() = "Cart at $currentPos"



    fun placedOnTrack(direction: Char) {
        when (direction) {
            '-', '|', '^', '>', 'v','<'  -> { /* do nothing, straight track */ }
            '+' -> intersect()
            '/' -> turn(if (currentDirection % 2 == 0) RIGHT else LEFT)
            '\\' -> turn(if (currentDirection % 2 == 0) LEFT else RIGHT)
        }
    }

    fun move(network: Map<Coordinate, Track>): List<Cart>?{
        if (!alive) return null
        val nextTrack = with (currentPos) {
            when (currentDirection){
                NORTH -> Coordinate(x, y-1)
                EAST -> Coordinate(x+1, y)
                SOUTH -> Coordinate(x, y+1)
                WEST -> Coordinate(x-1, y)
                else -> error ("ERROR")
            }
        }
        network[nextTrack]!!.placeCartOnTrack(this)?.let{
            return it
        }
        return null
    }

    private var currentDirection = when (direction) {
        '^' -> NORTH
        '>' -> EAST
        'v' -> SOUTH
        '<' -> WEST
        else -> error("Bad direction: $direction")
    }


    private fun turn(direction: Int) {
        currentDirection = (currentDirection + 4 + direction) % 4
    }
    private fun intersect() {
        turn(nextIntersection)
        nextIntersection = nextIntersection.increaseWithinBounds(-1, 1)
    }


    companion object {
        private const val NORTH = 0
        private const val EAST = 1
        private const val SOUTH = 2
        private const val WEST = 3

        private const val LEFT: Int = -1
        private const val STRAIGHT = 0
        private const val RIGHT: Int = 1
    }
}


