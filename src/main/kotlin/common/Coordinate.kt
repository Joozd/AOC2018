package common

// because Pairs are too confusing for Joozd
open class Coordinate(val x: Int, val y: Int): Comparable<Coordinate>{
    override fun equals(other: Any?) = if (other !is Coordinate) false else
        other.x == x && other.y == y
    override fun toString() = "($x, $y)"

    override fun hashCode(): Int  = x.hashCode()*53 * y.hashCode()
    override fun compareTo(other: Coordinate): Int = if (y == other.y) x-other.x else y - other.y

    fun north() = Coordinate(x-1, y)
    fun south() = Coordinate(x+1, y)
    fun east() = Coordinate(x, y+1)
    fun west() = Coordinate(x, y-1)
}