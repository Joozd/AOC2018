package day19

import common.Device
import common.Solution

class Day19: Solution {
    override val day = 19

    private val input = inputLines()

    private val device = Device()

    override fun answer1(): Any = altq1()

    override fun answer2(): Any {
        device.setReg(0, 1)
        device.runProgram(input.dropLast(1))
        val numberToFactor = device[1]
        return factor(numberToFactor)
    }

    private fun altq1(): Int{
        return factor(860)
    }

    private fun factor(number: Int): Int{
        var x = 1
        var sum = 0
        while(x*2 <= number){
            if (number % x == 0) sum += x
            x++
        }
        return sum + number

    }
}