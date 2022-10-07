package day15

import common.CoordinateWithChar
import common.Solution
import common.dijkstra.Node
import common.dijkstra.RouteFinder
import common.extensions.addNotNull

class Day15: Solution {
    override val day = 15
    private val input = inputLines()

    override fun answer1(): Any {
        val map = LinkedHashMap<Node<Int>, Position>()
        val routeFinder = RouteFinder<Int>{ node -> map.findPosition(node) }
        val creatures: MutableList<Creature> = map.fillMapAndReturnCreatures(routeFinder, 3)

        var rounds = 0
        //println("*****\nRound $rounds")
        //println(CoordinateWithChar.plotMap(map.values.toList()))

        while (!somebodyWon(creatures)) {
            var roundIncomplete = false
            println("*****\nRound ${rounds + 1}")
            creatures.sorted().forEach {
                if (somebodyWon(creatures)) roundIncomplete = true
                it.move()
                it.attack()
            }
            if (!roundIncomplete)
                rounds++
            println(CoordinateWithChar.plotMap(map.values.toList()))

        }
        return calculateScore(rounds, creatures)
    }

    override fun answer2(): Any {
        var elvesPower = 13

        while (true){
            val map = LinkedHashMap<Node<Int>, Position>()
            val routeFinder = RouteFinder<Int>{ node -> map.findPosition(node) }
            elvesPower++
            var rounds = 0
            var elfDied = false
            val creatures: MutableList<Creature> = map.fillMapAndReturnCreatures(routeFinder, elvesPower)
            println("\nTrying with $elvesPower power!")
            while(!elfDied && !somebodyWon(creatures)){
                //println(CoordinateWithChar.plotMap(map.values.toList()))
                //println(".-|-.")
                var roundIncomplete = false
                // println("*****\nRound ${rounds + 1}")
                creatures.sorted().forEach {
                    if (somebodyWon(creatures)) roundIncomplete = true
                    it.move()
                    if (it.attack() is Elf) elfDied = true
                }
                if (!roundIncomplete)
                    rounds++
                // println(CoordinateWithChar.plotMap(map.values.toList()))
            }
            if (somebodyWon(creatures) && !elfDied) {
                println(CoordinateWithChar.plotMap(map.values.toList()))
                return calculateScore(rounds, creatures)
            }
        }
    }

    /**
     * Fill map with all non-wall positions
     */
    private fun MutableMap<Node<Int>, Position>.fillMapAndReturnCreatures(routeFinder: RouteFinder<Int>, elvesPower: Int): MutableList<Creature>{
        val foundCreatures = ArrayList<Creature>()
        CoordinateWithChar.fromListOfStrings(input)
            .filter{ it.c != '#' }
            .let { allPositions ->
                allPositions.forEachIndexed { index, it ->
                    Position.make(it, allPositions).let { pos ->
                        this[pos] = pos
                        foundCreatures.addNotNull(makeCreature(it.c, routeFinder, this, foundCreatures, index, elvesPower))?.moveTo(pos)
                    }
                }
            }
        return foundCreatures
    }

    private fun makeCreature(c: Char, routeFinder: RouteFinder<Int>, map: Map<Node<Int>, Position>, allCreatures: MutableList<Creature>, index: Int, elvesAP: Int) = when(c){
        'E'-> Elf(routeFinder, map, allCreatures, index, elvesAP)
        'G'-> Goblin(routeFinder, map, allCreatures, index)
        else -> null
    }

    private fun MutableMap<Node<Int>, Position>.findPosition(position: Node<Int>) = this[position]?.takeIf { it.occupiedBy == null }

    private fun somebodyWon(creatures: List<Creature>) = creatures.all { it is Elf} || creatures.all { it is Goblin }

    private fun calculateScore(rounds: Int, creatures: List<Creature>): Int{
        val creatureScore = creatures.sumOf { it.hp }
        println("***********\nFINAL SCORE:\n***********\n")
        println(creatures.joinToString("\n"))
        println("Score: $rounds * $creatureScore = ${rounds*creatureScore}")
        return rounds * creatureScore
    }
}