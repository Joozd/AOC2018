package day6

import common.Coordinate
import common.extensions.abs

class GridLocation(x: Int, y:Int, private val targets: List<Coordinate>): Coordinate(x, y) {
    private fun distanceTo(target: Coordinate) = (target.x - x).abs() + (target.y - y).abs()
    private var cachedDistances: List<Int>? = null
    private var closestCache: Coordinate? = null

    fun closest(): Coordinate?{
        if (closestCache != null) return closestCache
        cacheDistances()

        val closest = cachedDistances!!.minOrNull().takeIf { d -> cachedDistances!!.count { it == d} == 1}

        return closest?.let{ d ->
            targets[cachedDistances!!.indexOf(d)].also{
                closestCache = it
            }
        }

    }

    private fun cacheDistances(){
        if (cachedDistances == null)
            cachedDistances = targets.map {distanceTo(it)}
    }

    fun totalDistance(): Int {
        cacheDistances()
        return cachedDistances!!.sum()
    }

}