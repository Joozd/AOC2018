package day15

import common.Coordinate
import common.dijkstra.Node
import common.dijkstra.RouteFinder

class Elf(routeFinder: RouteFinder<Int>, map: Map<Node<Int>, Position>, allCreatures: MutableList<Creature>, private val id: Int, attackPower: Int): Creature(routeFinder, map, allCreatures, ap = attackPower) {
    /**
     * Filter all enemies from a List of [Creature]
     */
    //override fun filterEnemies(allCreatures: List<Creature>) = allCreatures.filterIsInstance<Goblin>()


    override fun toString() = "Elf #${id.toString().padEnd(2, ' ')}   : hp = $hp, ap = $ap"
}