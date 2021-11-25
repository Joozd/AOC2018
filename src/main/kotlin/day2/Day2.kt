package day2

import common.Solution
import common.extensions.countIndexed

class Day2: Solution {
    override val day = 2

    private val input = inputLines()

    override fun answer1(): Int {
        val twos = input.filter { it.hasExactlyOfTheSameLetter(2) }.size
        val threes = input.filter { it.hasExactlyOfTheSameLetter(3) }.size
        return twos * threes
    }

    override fun answer2(): String {
        val result = input.firstNotNullOf{ l -> l.getAllStringsWithOneDifferentLetter(input).takeIf {it.isNotEmpty()}}
        return result.matchingOnly()
    }

    private fun String.hasExactlyOfTheSameLetter(amount: Int): Boolean{
        val letters = this.toSet()
        return letters.any {l -> this.count { it == l } == amount}
    }

    private fun String.hasHowManyDifferentLetters(other: String): Int =
        this.toList().countIndexed { i, c -> c != other[i] }

    private fun String.hasExactlyOneDifferentLetterThan(other: String): Boolean =
        this.hasHowManyDifferentLetters(other) == 1

    private fun String.getAllStringsWithOneDifferentLetter(others: List<String>): List<String> {
        others.filter { it.hasExactlyOneDifferentLetterThan(this) }.let{
            return if (it.isNotEmpty()) it + this
            else emptyList()
        }
    }

    private fun List<String>.matchingOnly(): String =

        this.first().filterIndexed { index, c ->
            this.all { it[index] == c }
        }



}