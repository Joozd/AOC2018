package day20

import common.Coordinate

class Node(val route: String) {
    fun pathsFromHere(): List<String>{
        val paths = ArrayList<String>()
        val iterator = route.iterator()
        val sb = StringBuilder()
        var depth = 0
        while (iterator.hasNext()){
            val c = iterator.next()
            when(c){
                '(' -> depth++
                ')' -> depth--
            }
            if (c == '|' && depth == 0) {
                paths.add(sb.toString())
                sb.clear()
            }
            else sb.append(c)
        }
        return paths + sb.toString()
    }


    //returns a list of all endpoints from the paths from this node
    fun fillUniverse(universe: MutableMap<Coordinate, Room>, currentRoom: Room): List<Room> =
        pathsFromHere().map{ path ->
            fillUniverseFromPath(path, universe, currentRoom)
        }.flatten()

    private fun fillUniverseFromPath(path: String, universe: MutableMap<Coordinate, Room>, currentRoom: Room): List<Room>{
        // println("Filling universe of size ${universe.size} from $currentRoom with path (length = ${path.length} ${path.take(100)}")
        if (path in currentRoom.searchedStrings) return emptyList()
        currentRoom.searchedStrings.add(path)
        var workingRoom = currentRoom
        val iterator = path.iterator()
        while (iterator.hasNext()){
            val c = iterator.next()
            if (c == '(') {
                val inBrackets = getStringInBrackets(iterator)
                val endPoints = Node(inBrackets).fillUniverse(universe, workingRoom)
                val remainingPath = iterator.remainingToString()
                return if (remainingPath.isNotBlank())
                    endPoints.map { fillUniverseFromPath(remainingPath, universe, it) }.flatten().distinct()
                else endPoints
            }
            else workingRoom = workingRoom.addExit(c, universe)
        }
        return listOf(workingRoom)
    }

    private fun getStringInBrackets(iterator: Iterator<Char>): String{
        var depth = 1
        val sb = StringBuilder()
        while(depth > 0){
            val c = iterator.next()
            when(c){
                '(' -> depth++
                ')' -> depth--
            }
            //println("$c / $depth")
            sb.append(c)
        }
        //println(sb)
        sb.deleteCharAt(sb.length - 1) // we didn't get opening bracket, we can remove closing one
        return sb.toString()
    }

    private fun Iterator<Char>.remainingToString(): String =
        StringBuilder().apply {
            forEachRemaining(this::append)
        }.toString()



}