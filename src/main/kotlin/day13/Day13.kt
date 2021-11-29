package day13

import common.dijkstra.Coordinate
import common.Solution

class Day13: Solution {
    override val day = 13

    private val input = inputLines()//testNetwork//

    override fun answer1(): Any {
        val network = fillNetwork()
        val carts = getCarts(network)

        while (true){
            carts.sortedBy { it.currentPos }.let{ cc ->
                cc.forEach {
                    if (it.move(network) != null) return it
                }
            }
        }
    }

    override fun answer2(): Any? {
        val network = fillNetwork()
        val carts = getCarts(network).toMutableList()

        while(carts.size > 1){
            carts.sortedBy { it.currentPos }.let{ cc ->
                cc.forEach { cart ->
                    cart.move(network)?.let{ carts.removeAll(it) }
                }
            }
        }
        return carts.firstOrNull()
    }

    private fun fillNetwork(): MutableMap<Coordinate, Track> {
        val network = mutableMapOf<Coordinate, Track>()
        makeTracksFromInput().forEach {
            network[it] = it
        }
        return network
    }

    private fun makeTracksFromInput() = input.mapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
            if (c == ' ') null
            else Track(x,y,c)
        }
    }.flatten()

    private fun getCarts(network: Map<Coordinate, Track>) = input.mapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
            if (c in "^>v<") Cart(network[Coordinate(x, y)]!!, c).apply{
                currentPos.placeCartOnTrack(this)
            }
            else null
        }
    }.flatten()
}