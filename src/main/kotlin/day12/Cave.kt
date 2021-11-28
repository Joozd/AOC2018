package day12

import common.extensions.findPeriodOfGrowth
import common.extensions.words

/**
 * A Cave holds a row of pots.
 */
class Cave(private var rowOfPots: String, rules: List<String>) {
    private val growthRules = mutableMapOf<String, Char>().apply {
        rules.forEach {
            this[it.words().first()] = it.last()
        }
    }

    private var ticks = 0

    private val numberCorrection get() = ticks * -2

    private var lastKnownSize = countPlants(rowOfPots)

    private fun findPeriodOfGrowth(): List<Int>{
        // Lets start by doing 100 ticks
        val growth = ArrayList<Int>()

        var foundPeriod: List<Int>? = null
        while (foundPeriod == null){
            growth.add(tickAndReturnGrowth())
            foundPeriod = growth.findPeriodOfGrowth()
        }
        return foundPeriod
    }

    /**
     * Grows plants in place
     */
    private fun tick(){
        rowOfPots = growPlants(rowOfPots)
        ticks++
    }

    fun answer1(ticks: Int): Int{
        repeat(ticks){
            tick()
        }
        return countPlants(rowOfPots)
    }

    /**
     * This is a bit more complicated than needed, but also works for oscillating growth.
     */
    fun answer2(required: Long = 50000000000): Long{
        val period = findPeriodOfGrowth().map {it.toLong()}
        val currentTick = ticks.toLong()
        val ticksLeft = required - currentTick
        val periodsRequired = ticksLeft / period.size
        val totalIncreasePerPeriod = period.sum()
        val remainingIncrease = ticksLeft % currentTick

        val completePeriodsIncrease = totalIncreasePerPeriod * periodsRequired
        val remainingPeriodIncrease = period.take(remainingIncrease.toInt()).sum()

        return completePeriodsIncrease + remainingPeriodIncrease + countPlants(rowOfPots)
    }

    private fun tickAndReturnGrowth(): Int{
        tick()
        val previousSize = lastKnownSize
        return (countPlants(rowOfPots).also{ lastKnownSize = it} - previousSize)
    }

    /**
     * Grows plants for a single iteration
     */
    private fun growPlants(currentRow: String): String{
        val plants = "..$currentRow.."
        val plantsList = plants.indices.map {
            checkIfThisWillBeAPlant(it, plants)
        }
        return plantsList.joinToString("")
    }

    private fun checkIfThisWillBeAPlant(index: Int, currentRow: CharSequence): Char{
        val relevantPlants = getRelevantPlants(index, currentRow)
        return growthRules[relevantPlants]!!
    }

    private fun getRelevantPlants(index: Int, currentRow: CharSequence): String {
        require (index in currentRow.indices) { "Index $index out of range in length ${currentRow.length} - $currentRow"}
        val prefix = maxOf(2 - index, 0).let {
            ".".repeat(it)
        }
        val postfix = maxOf(3 - (currentRow.length - index), 0).let {
            ".".repeat(it)
        }
        val startIndex = maxOf(0, index - 2)
        val endIndex = minOf(currentRow.length - 1, index + 2)
        return prefix + currentRow.slice(startIndex..endIndex) + postfix
    }

    private fun countPlants(plants: String): Int{
        var currentPot = numberCorrection
        var currentValue = 0
        for (pot in plants){
            if (pot == '#' ){
                currentValue += currentPot
            }
            currentPot++
        }
        return currentValue
    }
}