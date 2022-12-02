package day25

import common.extensions.abs
import common.extensions.grabInts

class Point(val w: Int, val x: Int, val y: Int, val z: Int) {
    fun distanceTo(other: Point) =
        (w-other.w).abs() + (x-other.x).abs() + (y-other.y).abs() + (z-other.z).abs()

    fun fitsInConstellation(constellation: Collection<Point>) =
        constellation.any{
            distanceTo(it) <= 3
        }

    companion object{
        fun ofLine(line: String) =
            line.grabInts().let{
                Point(it[0], it[1], it[2], it[3])
            }
    }
}