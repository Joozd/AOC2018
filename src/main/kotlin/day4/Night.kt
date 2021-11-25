package day4

import common.extensions.words

class Night(val guard: Int) {
    private val sleeps =  ArrayList<IntRange>()

    private var fellAsleepTime: Int? = null

    private fun fallAsleep(time: Int){
        fellAsleepTime = time
    }

    private fun wakeUp(time: Int) {
        (fellAsleepTime ?: error("Cant wake up if not asleep")).let {
            sleeps.add(it..time)
            fellAsleepTime = null
        }
    }

    fun amountOfSleep() = sleeps.sumOf { it.count() }

    fun minutesAsleep(): List<Int> = sleeps.map{ it.toList() }.flatten()

    fun action(action: String){
        val words = action.words()
        val minutes = words[1].drop(3).dropLast(1).toInt()
        if (words[2] == "falls") fallAsleep(minutes)
        else wakeUp(minutes)
    }

    companion object{
        fun of(line: String) = Night(line.words().first { it.startsWith('#')}.drop(1).toInt())
    }
}