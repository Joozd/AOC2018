package day12

import common.Solution
import common.extensions.words

class Day12: Solution {
    override val day = 12
    val input = inputLines()
    private lateinit var cave: Cave

    override fun answer1(): Any {
        cave = Cave(input.first().words().last(), input.drop(2))

        return cave.answer1(20)

    }

    override fun answer2() = cave.answer2(50000000000)
}