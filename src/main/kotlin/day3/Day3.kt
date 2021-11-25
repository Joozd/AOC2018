package day3

import common.Solution

class Day3: Solution {
    override val day = 3

    private val input = inputLines()

    private var claims: List<Claim>? = null

    override fun answer1(): Int {
        claims = input.map { Claim.ofLine(it)}

        //lets go bruteforce:
        val canvas = Array(1000){ Array(1000) { 0 } }

        claims!!.forEach{ claim ->
            claim.coordinates().forEach {
                canvas[it.x][it.y]++
            }
        }

        return canvas.flatten().filter{ it > 1}.size


    }

    override fun answer2() = claims!!.first { c -> claims!!.none { it.overlaps(c) } }
}