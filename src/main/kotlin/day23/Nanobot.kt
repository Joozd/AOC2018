package day23

import common.XYZCoordinate
import common.extensions.grabInts

/*
 * A Nanobot sphere is actually an octagon.
 * There is a point along the x, y and z axis, and between those points a plane.
 * Two octagons overlap when:
 *  - any point of this is inside the other sphere, or
 *  - any point of the other is in this sphere
 * Since they all have the same shape, it cannot be only intersecting edges,
 * so we only need to look at the extremes to see if spheres are overlapping.
 */
class Nanobot(x: Int, y:Int, z: Int, val range: Int): XYZCoordinate(x, y, z) {
    fun canReach(other: XYZCoordinate): Boolean{
        return distanceTo(other) <= range
    }

    val extremes get() = listOf(
        copy(x = x + range),
        copy(x = x - range),
        copy(y = y + range),
        copy(y = y - range),
        copy(z = z + range),
        copy(z = z - range)
    )

    companion object{
        fun ofLine(line: String): Nanobot{
            val vv = line.grabInts()
            return Nanobot(vv[0], vv[1], vv[2], vv[3])
        }
    }
}