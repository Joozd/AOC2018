package day25

import common.Solution

class Day25: Solution {
    override val day = 25

    override fun answer1(): Any{
        val constellations = (inputLines().map{ // (t1.lines().map {  //
            listOf(Point.ofLine(it))
        }).toMutableList()
        while(true){
            val collidingConstellation = constellations.firstOrNull { it.findCollision(constellations) != null } ?: break
            val foundConstellations = collidingConstellation.findCollisions(constellations)
            constellations.removeAll(foundConstellations.toSet())
            constellations.add(foundConstellations.flatten())
        }
        return constellations.size
    }

    override fun answer2(): Any = inputLines().first()

    private fun List<Point>.findCollision(universe: List<List<Point>>) =
        universe.firstOrNull { it.collidesWith(this) }

    private fun List<Point>.findCollisions(universe: List<List<Point>>): List<List<Point>> =
        listOf(this) + universe.filter { it.collidesWith(this) }

    private fun List<Point>.collidesWith(other: List<Point>) =
        this !== other && any { it.fitsInConstellation(other) }
}