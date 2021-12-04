package day15

import common.dijkstra.Node
import common.dijkstra.RouteFinder

class Goblin(routeFinder: RouteFinder<Int>, map: Map<Node<Int>, Position>, allCreatures: MutableList<Creature>, private val id: Int, ap: Int = 3): Creature(routeFinder, map, allCreatures, ap) {
    /**
     * Filter all enemies from a List of [Creature]
     */
    //override fun filterEnemies(allCreatures: List<Creature>) = allCreatures.filterIsInstance<Elf>()

    override fun toString() = "Goblin #${id.toString().padEnd(2, ' ')}: hp = $hp"
}