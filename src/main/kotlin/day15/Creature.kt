package day15

import common.dijkstra.Node
import common.dijkstra.RouteFinder

abstract class Creature(private val routeFinder: RouteFinder<Int>, private val map: Map<Node<Int>, Position>, private val allCreatures: MutableList<Creature>, protected val ap: Int): Comparable<Creature> {
    var hp = 200
    var currentPos: Position? = null
        private set

    private val dead get() = hp <= 0

    fun moveTo(target: Position){
        currentPos?.occupiedBy = null
        currentPos = target
        target.occupiedBy = this
    }

    fun openNeighbours(): List<Position> = currentPos!!.getNeighbours().mapNotNull { map[it] }.filter {it.occupiedBy == null}

    /**
     * Moves if not next to an enemy
     */
    fun move(){
        if (enemyInRange() == null) findRouteToNearestEnemy()?.let { moveTo(it) }
    }

    /**
     * Attacks an enemy if one is close enough
     */
    fun attack(): Creature? =
        if (!dead) enemyInRange()?.hit(ap)   //.also{ enemyInRange()?.let { println("${this.toString().padEnd(30, ' ')} Hit $it for $ap") }}
        else null


    private fun hit(damage: Int): Creature?{
        hp -= damage
        return if (checkIfDead()) this else null
    }

    private fun checkIfDead(): Boolean{
        if (hp <= 0){
            //println("$this ded")
            currentPos!!.occupiedBy = null
            allCreatures.remove(this)
            //println("$this dead")
            return true
        }
        return false
    }

    override fun compareTo(other: Creature): Int =
        currentPos!!.compareTo(other.currentPos!!)


    /**
     * Filter all enemies from a List of [Creature]
     */
    private fun filterEnemies(allCreatures: List<Creature>): List<Creature> = allCreatures.filter { this::class != it::class }

    private fun enemyInRange(): Creature? = isNextToCreature(filterEnemies(allCreatures))

    /**
     * Returns the first step on the route to the nearest enemy
     * Suggest filtering all creatures down to only enemies and call [findRouteToNearestCreature]
     */
    private fun findRouteToNearestEnemy(): Position? = findRouteToNearestCreature(filterEnemies(allCreatures))

    private fun isNextToCreature(allCreatures: List<Creature>): Creature? =
        currentPos?.getNeighbours()?.map { map[it]!! }
            ?.sorted()
            ?.filter{ it.occupiedBy in allCreatures}
            ?.mapNotNull{ it.occupiedBy }
            ?.let{ allCreaturesInRange ->
                allCreaturesInRange.filter { c -> c.hp == allCreaturesInRange.minOfOrNull { it.hp }}
            }?.firstOrNull()

    private fun findRouteToNearestCreature(allCreatures: List<Creature>): Position?{
        val startPoints = openNeighbours()
        val allPositionsInRange = getAllPositionsInRangeOfACreature(allCreatures)
        val allRoutes = startPoints.map{ start ->
            allPositionsInRange.mapNotNull{ end ->
                routeFinder.findRoute(start, end)
            }
        }.flatten()
        if (allRoutes.isEmpty()) return null
        val shortestDistance = allRoutes.minOf { routeFinder.findDistance(it)!! }
        val shortestRoutes: List<List<Node<Int>>> = allRoutes.filter { routeFinder.findDistance(it) == shortestDistance}
        return shortestRoutes.map { it.first() }
            .map { map[it]!! }.minOrNull()

        /*
        getAllPositionsInRangeOfACreature(allCreatures).mapNotNull{
            routeFinder.findRoute(
                currentPos!!,
                it){ pos ->
                findPosition(pos, this, it.occupiedBy!!)
            }
        }.minByOrNull {
            routeFinder.findDistance(it)!!
        }?.let {
            map[it[1]]
        }
        */
    }

    private fun getAllPositionsInRangeOfACreature(allCreatures: List<Creature>): List<Position> =
        allCreatures.map{it.openNeighbours()}.flatten().distinct()

    private fun findPosition(position: Node<Int>, vararg allowedCreatures: Creature) = map[position]?.takeIf { it.occupiedBy == null || it.occupiedBy in allowedCreatures }
}