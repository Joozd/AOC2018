import common.Solution
import common.Coordinate
import common.dijkstra.Node
import common.dijkstra.RouteFinder
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DijkstraTest: Solution {
    override val day = 13

    private val input by lazy { inputLines() }


    override fun answer1() = Unit //need to have this to implement Solution

    override fun answer2() = Unit //need to have this to implement Solution

    private val network = HashMap<CoordinateNode, NetworkedTrack>()
    private val routeFinder = RouteFinder<Int>{
        network[it] ?: error ("Cannot find $it in network")
    }

    @Before
    fun setup(){
        val tracks = makeTracksFromInput()
        tracks.forEach {
            it.fillExits(tracks)
            network[it] = it
        }

    }

    @Test
    fun testDijkstraNetwork(){
        assertEquals(2, routeFinder.findDistance(NetworkedTrack(0,16), NetworkedTrack(0,18)))
        val someRoute = routeFinder.findRoute(NetworkedTrack(0,16), NetworkedTrack(21,43))?.map {it as NetworkedTrack}
        println(someRoute?.map { Coordinate(it.x, it.y) }?.joinToString("\n"))
    }

    private fun makeTracksFromInput() = input.mapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
            if (c == ' ') null
            else NetworkedTrack(x,y,c)
        }
    }.flatten()


    private class NetworkedTrack(x: Int, y: Int, private val c: Char? = null): CoordinateNode(x,y), Node<Int>{
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
            val neighbours = ArrayList<CoordinateNode>()
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



