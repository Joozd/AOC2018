package common

import common.extensions.words
import java.io.File
import kotlin.system.measureNanoTime

interface Solution {
    val day: Int
    fun answer1(): Any?

    fun answer2(): Any?

    operator fun invoke() {
        println("Answers for day $day:")
        println("1:")
        println(answer1())
        println("2:")
        println(answer2())
    }

    fun runTimed() {
        println("Answers for day $day:")
        val a1: Any?
        val a2: Any?
        val duration1 = measureNanoTime { a1 = answer1() }
        println("1:")
        println(a1)
        val duration2 = measureNanoTime { a2 = answer2() }
        println("2:")
        println(a2)
        val durationTotal = duration1 + duration2
        println("time 1:     ${duration1/1000000.toFloat()} ms")
        println("time 2:     ${duration2/1000000.toFloat()} ms")
        println("total time: ${durationTotal/1000000.toFloat()} ms")
    }


    fun inputLines() = readInput().lines()
    fun inputNumbers(): List<Int> {
        val input = readInput()
        return if ('\n' in input)
            input.lines().map { it.toInt()}
        else input.words().map {it.toInt()}
    }
    fun readInput() = File("inputs\\$day.txt").readText()
}