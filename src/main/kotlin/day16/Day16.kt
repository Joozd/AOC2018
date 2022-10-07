package day16

import common.Device
import common.Solution
import common.extensions.grabInts

class Day16: Solution {
    override val day = 16

    private val input = readInput().lines().joinToString("\n")

    private val tests = input.split("\n\n\n\n").first().split("\n\n").map{ Device.Test.ofString(it)}

    private val program = input.split("\n\n\n\n").last().lines().map{it.grabInts()}

    private val device = Device()

    override fun answer1(): Any {
        return tests.filter { device.possibleOpcodes(it).size >=3 }.size
    }

    override fun answer2(): Any {
        device.findOpCodes(tests)
        return device.run(program)
    }
}