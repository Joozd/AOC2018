package day5

import common.Solution
import java.util.*

class Day5: Solution {
    override val day = 5
    private val input = readInput()

    override fun answer1(): Int {
        /*
        // Regex version was 690 ms

        val alphabet = buildAlphabet()
        val pairs = alphabet.map {makePair(it)}.flatten()
        //we now have a list of [aA, Aa, bB, Bb, .... zZ, Zz]
        val regex = pairs.joinToString("|").toRegex()
        var current = input.replace(regex, "")
        do{
            val prevLength = current.length
            current = current.replace(regex, "")
        } while (prevLength > current.length)
         */

        //reduce version was 51ms
        return input.reduce().length
    }

    override fun answer2(): Int {
        val result = buildAlphabet()
            .map {
                input.replace(it.toString(), "", ignoreCase = true)
                    .reduce()
        }
        return result.minByOrNull { it.length }!!.length
    }

    private fun buildAlphabet(): List<Char> = (0..25).map { 'a' + it}

    /**
     * makes a list of "Xx", "xX"
     */
    private fun makePair(c: Char): List<String> = listOf(
        "$c${c.uppercaseChar()}",
        "${c.uppercaseChar()}$c"
    )

    private fun CharSequence.reduce(): CharSequence{
        val lastIndex = indices.last
        val sb = StringBuilder(this.length)
        var skipNext: Boolean = false
        this.forEachIndexed { i, c ->
            if (skipNext) skipNext = false
            else{
                if (i != lastIndex && this[i+1] == c.switchCase()){
                    skipNext = true
                }
                else sb.append(c)
            }
        }
        return if (sb.length == this.length)  this else sb.reduce()
    }

    private fun Char.switchCase() = if (isLowerCase()) uppercaseChar() else lowercaseChar()

}