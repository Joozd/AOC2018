package day1

import common.Solution

class Day1: Solution {
    override val day = 1

    private val input = inputNumbers()

    override fun answer1() = input.sum()

    override fun answer2(): Long {
        val entries = input.size
        var counter = 0
        var currentFreq = 0L
        val foundFreqs = HashSet<Long>()
        while (true){
            currentFreq += input[counter++ % entries]
            if (currentFreq in foundFreqs) {
                println("found after $counter steps")
                return currentFreq
            }
            else foundFreqs.add(currentFreq)
        }
    }
}