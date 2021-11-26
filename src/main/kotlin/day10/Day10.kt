package day10

import common.Solution

class Day10: Solution {
    override val day = 10
    private val input = inputLines()
    private lateinit var constellation: Constellation

    override fun answer1(): String {
        constellation = Constellation(input)

        runConstellationToSmallestSize(constellation)

        return constellation.toString()
    }

    override fun answer2() = constellation.ticks

    /**
     * Runs a constellation until the next step will make it bigger (in place)
     */
    private fun runConstellationToSmallestSize(constellation: Constellation){
        var currentHeight = constellation.height()
        var previousHeight = Int.MAX_VALUE

        while(currentHeight < previousHeight){
            previousHeight = currentHeight
            constellation.saveState()
            constellation.tick()
            currentHeight = constellation.height()
        }
        constellation.restoreState()
    }
}