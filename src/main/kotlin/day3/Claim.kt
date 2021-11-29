package day3

import common.dijkstra.Coordinate
import common.extensions.words

class Claim(val id: Int, val left: Int, val right: Int, val top: Int, val bottom: Int) {
    override fun toString(): String = "Claim #$id: $left, $right, $top, $bottom"

    fun coordinates(): List<Coordinate> =
        (left..right).map{ h ->
            (top..bottom).map {v -> Coordinate(h, v) }
        }.flatten()

    fun overlaps(other: Claim): Boolean =
        // Claim won't overlap with itself
        if (other === this) false else
        (other.left in left..right || other.right in left..right || left in other.left..other.right) // other starts or ends in this, or this starts in other (if other completely surrounds this)
                && (other.top in top..bottom || other.bottom in top..bottom || top in other.top..other.bottom)

    companion object{
        fun ofLine(line: String): Claim {
            with (line.words()){
                return Claim(getID(), getLeft(), getRight(), getTop(), getBottom())
            }
        }

        private fun List<String>.getID() = first().drop(1).toInt()

        private fun List<String>.getLeft() = getPos()[0]

        private fun List<String>.getRight() = getLeft() + getSize()[0] - 1

        private fun List<String>.getTop() = getPos()[1]

        private fun List<String>.getBottom() = getTop() + getSize()[1] - 1


        // list of [left, top]
        private fun List<String>.getPos() = get(2).dropLast(1).split(",").map{it.toInt()}

        //list of [width, height]
        private fun List<String>.getSize() = last().split('x').map { it.toInt() }
    }
}