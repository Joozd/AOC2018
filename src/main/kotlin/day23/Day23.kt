package day23

import common.Solution
import common.extensions.abs

class Day23: Solution {
    override val day = 23

    private val input = inputLines()

    private lateinit var swarm: List<Nanobot>

    override fun answer1(): Any {
        swarm = input.map { Nanobot.ofLine(it) }.sortedBy { it.x.abs() + it.y.abs() + it.z.abs() } // sorted by manhattan distance
        val nanobotWithBiggestRange = swarm.maxBy { it.range }
        return swarm.filter { nanobotWithBiggestRange.canReach(it) }.size
    }

    /*
     * The area with the biggest overlap will always have at least one extreme (octagon point) at it's border.
     * That extreme is not necessarily at the closest point (the closest point might be an intersection).
     * However, that intersection must be at the border of the farthest Nanobots range (by distance minus range)
     */
    override fun answer2(): Any {
        // In case there are multiple clusters tied, this does not check which one is closest.
        val pointInAllOverlapping = swarm.map { it.extremes }
            .flatten()
            .distinct()
            .maxBy { swarm.count { n -> n.canReach(it) }}

        // closest point of the farthest nanobot that can reach the point which is in all overlapping nanobots' reach
        return swarm.filter { it.canReach(pointInAllOverlapping) }
            .maxOf { it.distanceToZero() - it.range }
    }
}