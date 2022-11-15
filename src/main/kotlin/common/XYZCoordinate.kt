package common

import common.extensions.abs

open class XYZCoordinate(val x: Int, val y: Int, val z: Int) {
    fun copy(x: Int = this.x, y: Int = this.y, z: Int = this.z) =
        XYZCoordinate(x,y,z)

    fun distanceTo(other: XYZCoordinate) =
        (other.x - x).abs() + (other.y - y).abs() + (other.z - z).abs()

    fun distanceToZero() = x.abs() + y.abs() + z.abs()

    override fun toString() = "($x,$y,$z)"
}