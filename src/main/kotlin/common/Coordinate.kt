package common

// because Pairs are too confusing for Joozd
open class Coordinate(val x: Int, val y: Int): Comparable<Coordinate>{
    override fun equals(other: Any?) = if (other !is Coordinate) false else
        other.x == x && other.y == y
    override fun toString() = "($x, $y)"

    override fun hashCode(): Int  = x.shl(16) + y
    override fun compareTo(other: Coordinate): Int = if (y == other.y) x-other.x else y - other.y
}