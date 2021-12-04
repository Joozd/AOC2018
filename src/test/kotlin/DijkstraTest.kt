import common.Solution
import common.Coordinate
import common.CoordinateWithChar
import common.dijkstra.GridNode
import common.dijkstra.Node
import common.dijkstra.RouteFinder
import day11.Grid
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DijkstraTest: Solution {
    override val day = 13

    private val input by lazy { inputLines() }


    override fun answer1() = Unit //need to have this to implement Solution

    override fun answer2() = Unit //need to have this to implement Solution

    private val network = HashMap<Node<Int>, NetworkedTrack>()

    private val gNetwork = HashMap<Node<Int>, NetworkedTrack>()

    private val routeFinder = RouteFinder<Int>{
        grabNode(it)
    }
    private val gRouteFinder = RouteFinder<Int>{
        grabGNode(it)
    }

    private fun grabNode(node: Node<Int>) =
        if (node is GridNode)
            error ("use grabGNode for grid nodes")
        else
            network[node] ?: error ("Cannot find $node in network")

    private fun grabGNode(node: Node<Int>) =
        gNetwork[node] ?: error ("Cannot find $node in network")


    @Before
    fun setup(){
        val tracks = makeTracksFromInput()
        tracks.forEach {
            network[it] = it.apply{
                fillExits(tracks)
            }
            gNetwork[it] = GN.of(it).apply{
                fillExits(tracks)
            }
        }
        println(network.size)
        println(gNetwork.size)
    }

    @Test
    fun testDijkstraNetwork(){
        assertEquals(network.size, gNetwork.size)
        // assertEquals(2, routeFinder.findDistance(NetworkedTrack(0,16), NetworkedTrack(0,18)))
        val start = grabNode(NetworkedTrack(0,16))
        val end = grabNode(NetworkedTrack(142,16))
        println(grabNode(start))
        val gnStart = grabGNode(GN.of(start))
        val gnEnd = grabGNode(GN.of(end))
        println("MAKING NORMAL ROUTE")
        println(start)
        println(end)
        val someRoute = routeFinder.findRoute(start, end)?.map {it as NetworkedTrack}

        println("MAKING GRID ROUTE")
        println(gnStart)
        println(gnEnd)
        val gnRoute = gRouteFinder.findRoute(gnStart, gnEnd)?.map {it as NetworkedTrack}

        println(CoordinateWithChar.plotMap(someRoute!!))
        println("//////\\\\\\\\\\\\//////\\\\\\\\\\\\//////")
        println(CoordinateWithChar.plotMap(gnRoute!!))

        assertEquals(routeFinder.findDistance(someRoute), routeFinder.findDistance(gnRoute))
        assertEquals(someRoute, gnRoute)
        //assertEquals(routeFinder.findDistance(someRoute!!), routeFinder.findDistance(start, end))
        //println(someRoute!!.map { Coordinate(it.x, it.y) }.joinToString("\n"))

    }

    private fun makeTracksFromInput() = input.mapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
            if (c == ' ') null
            else NetworkedTrack(x,y,c)
        }
    }.flatten()

    private class GN(x: Int, y: Int, c: Char?): NetworkedTrack(x,y,c), GridNode{
        companion object{
            fun of (nt: NetworkedTrack): GN = GN(nt.x, nt.y, nt.c)
        }
    }

    private open class NetworkedTrack(x: Int, y: Int, c: Char? = null): CoordinateWithChar(x,y,c ?: '%'), Node<Int>{
        private var exits: List<Int>? = null


        fun fillExits(allCoordinates: Collection<NetworkedTrack>){
            exits = when (c){
                '+' -> listOf(UP, LEFT, RIGHT, DOWN)
                '|', '^', 'v' -> listOf(UP, DOWN)
                '-', '<', '>' -> listOf (LEFT, RIGHT)
                '/' -> makeSlashExits(allCoordinates)
                '\\' -> makeBackslashExits(allCoordinates)
                else -> error ("bad input $c")
            }
        }

        private fun makeSlashExits(allCoordinates: Collection<NetworkedTrack>): List<Int> =
            if (verticalTrackAbove(allCoordinates))
                listOf(RIGHT, DOWN)
            else listOf(UP, LEFT)

        private fun makeBackslashExits(allCoordinates: Collection<NetworkedTrack>): List<Int> =
            if (verticalTrackAbove(allCoordinates))
                listOf(LEFT, DOWN)
            else listOf(UP, RIGHT)

        private fun verticalTrackAbove(allCoordinates: Collection<NetworkedTrack>) =
            this.y == 0 || (allCoordinates.firstOrNull { it == Coordinate(this.x, this.y - 1) }?.c ?: ' ') !in "\\/|+v^"

        companion object{
            private const val UP = 0
            private const val LEFT = 1
            private const val RIGHT = 2
            private const val DOWN = 3
        }

        override fun getNeighbours(): List<Node<Int>> {
            val neighbours = ArrayList<Node<Int>>()
            exits?.let { e ->
                if (UP in e) neighbours.add(NetworkedTrack(x, y-1))
                if (LEFT in e) neighbours.add(NetworkedTrack(x-1, y))
                if (RIGHT in e) neighbours.add(NetworkedTrack(x+1, y))
                if (DOWN in e) neighbours.add(NetworkedTrack(x, y+1))
            }
            return neighbours
        }

        override fun getDistanceToNeighbour(neighbour: Node<Int>): Int = 1

        override fun toString() = "NetworkedTrack: ($x, $y, $c) - exits are $exits (${getNeighbours().map { Coordinate((it as Coordinate).x, (it as Coordinate).y) }})"
    }

    abstract class CoordinateNode(x: Int, y: Int): Coordinate(x,y), Node<Int>
}



