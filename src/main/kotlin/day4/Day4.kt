package day4

import common.Solution
import common.extensions.addNotNull
import common.extensions.getMostOccurringFrequency
import common.extensions.getMostOccurringItem

class Day4: Solution {
    override val day = 4

    private val events = inputLines().sorted()
    private val trimSize = "[1518-05-25 00:46] ".length
    private val guardsMap = mutableMapOf<Int, MutableList<Night>>()



    override fun answer1(): Int {
        prepareMap()

        val mostSleepyGuard = guardsMap.keys.maxByOrNull { guard -> guardsMap[guard]!!.sumOf { it.amountOfSleep() } }!!

        val minutesAsleep = getMinutesAsleepForGuard(mostSleepyGuard)

        val mostSleepyMinute = minutesAsleep.getMostOccurringItem()!!

        return mostSleepyGuard * mostSleepyMinute
    }
    override fun answer2(): Int{
        val mostReliableGuard = guardsMap.keys.maxByOrNull { guard -> getMinutesAsleepForGuard(guard).getMostOccurringFrequency() }!!

        val mostSleepyMinute = getMinutesAsleepForGuard(mostReliableGuard).getMostOccurringItem()!!

        return mostReliableGuard * mostSleepyMinute
    }




    private fun buildNightsList(): List<Night>{
        val nights = ArrayList<Night>()
        var pointer = 0
        var currentNight: Night? = null
        while (pointer < events.size) {
            val currentEvent = events[pointer]
            if (currentEvent.drop(trimSize).startsWith("Guard")) {
                nights.addNotNull(currentNight)
                currentNight = Night.of(currentEvent)
            } else currentNight?.action(currentEvent)
            pointer++
        }
        return nights
    }

    private fun prepareMap(){
        val nights = buildNightsList()

        nights.forEach{
            if (it.guard !in guardsMap.keys) guardsMap[it.guard] = ArrayList()
            guardsMap[it.guard]!!.add(it)
        }

    }

    private fun getMinutesAsleepForGuard(guard: Int): List<Int> =
        guardsMap[guard]!!.map {it.minutesAsleep()}.flatten()


}