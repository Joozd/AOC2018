package day20

import common.Coordinate
import common.Solution

class Day20: Solution {
    override val day = 20

    val input = readInput().drop(1).dropLast(1) //

    var time = 0


    override fun answer1(): Any {
        val universe = HashMap<Coordinate, Room>()
        val currentRoom = Room(0,0)

        Node(input).fillUniverse(universe, currentRoom)

        val flooded = mutableSetOf(currentRoom.apply { flood() })

        repeat(999){ // 1 was already done
            flood(flooded)
        }
        println("Answer 2:")
        println(universe.values.filter { !it.flooded }.size)

        while (universe.values.any { !it.flooded}){
            flood(flooded)
        }

        return time
    }

    override fun answer2(): Any = "see other part of 1"

    private fun flood(currentlyFlooded: MutableSet<Room>) {
        val neighbours = currentlyFlooded.map{it.exits}.flatten().distinct()
        neighbours.forEach {
            it.flood()
            currentlyFlooded.add(it)
        }
        time++
    }

}