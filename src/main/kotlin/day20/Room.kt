package day20

import common.Coordinate

class Room(x: Int, y: Int): Coordinate(x, y) {
    constructor(c: Coordinate): this(c.x,c.y)

    val searchedStrings = ArrayList<String>()

    var flooded = false; private set

    fun flood(){
        flooded = true
    }

    val exits = HashSet<Room>(4)

    fun addExit(direction: Char, universe: MutableMap<Coordinate, Room>): Room{
        //println("Adding exit $direction to $this")
        val newCoordinate = when(direction){
            'N' -> north()
            'E' -> east()
            'S' -> south()
            'W' -> west()
            else -> error("ERROR MISLUKT AUB - $direction")
        }
        if (newCoordinate in exits) return universe[newCoordinate]!!
        val newRoom = universe[newCoordinate] ?: Room(newCoordinate)
        exits.add(newRoom)
        universe[newCoordinate] = newRoom
        //println(universe.size)
        return newRoom
    }
}