package day9

import common.Solution
import common.extensions.words

class Day9: Solution {
    override val day = 9
    private val input = readInput() // "10 players; last marble is worth 1618 points"
    private val amountOfPlayers = input.words()[0].toInt()
    private val lastMarble: Int = input.words()[6].toInt()

    override fun answer1() = playGame(amountOfPlayers, lastMarble)

    override fun answer2() = playGame(amountOfPlayers, lastMarble*100)


    private fun playGame(amountOfPlayers: Int, lastMarble: Int): Long?{
        val players = Array(amountOfPlayers){ 0L }
        var turn = 0
        var currentMarble = Marble(0)

        while (++turn <= lastMarble){
            val result = Marble(turn.toLong()).place(currentMarble)
            players[turn % amountOfPlayers] += result.score
            currentMarble = result.currentMarble
        }
        return players.maxOrNull()
    }
}