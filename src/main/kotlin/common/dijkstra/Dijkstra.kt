package common.dijkstra

class Dijkstra(private val start: Node<Int>, private val end: Node<Int>) {
    private val _route: List<NodeWithTotalDistanceAndPrevious> by lazy { generateRoute() }

    fun route() =
        _route.map { it.node}

    fun distance() =
        _route.last().totalDistance

    override fun toString() = _route.joinToString("") { "${it.totalDistance}: ${it.node} -> " }



    private fun generateRoute(): List<NodeWithTotalDistanceAndPrevious> { // need InitialDistance because I can't know what 0 I need as T
        val visitedNodes = HashSet<Node<Int>>()
        val unvisitedKnownNodes = HashSet<NodeWithTotalDistanceAndPrevious>()
        var currentNode = NodeWithTotalDistanceAndPrevious(start, 0, null)
        while(currentNode.node != end){
            unvisitedKnownNodes.addNodesIfCloser(makeNeighborsWithDistances(currentNode, visitedNodes))
            visitedNodes.add(currentNode.node)
            currentNode = unvisitedKnownNodes.popClosest() ?: throw (RouteNotFoundException())
            // if (visitedNodes.size % 100 == 0) println("visited: ${visitedNodes.size}, known: ${unvisitedKnownNodes.size}")
        }
        return buildRouteToCurrentNode(currentNode)
    }

    // iterate back to start, add to a list, then return that list inversed.
    private fun buildRouteToCurrentNode(currentNode: NodeWithTotalDistanceAndPrevious): List<NodeWithTotalDistanceAndPrevious> {
        var n = currentNode
        return buildList {
            while(true) {
                add(n)
                n = n.previousNode ?: break
            }
        }.reversed()
    }

    private fun makeNeighborsWithDistances(currentNode: NodeWithTotalDistanceAndPrevious, alreadyVisited: Collection<Node<Int>>): List<NodeWithTotalDistanceAndPrevious> =
        with(currentNode.node){
            getNeighbours()
                .filter{ it !in alreadyVisited }
                .map { n->
                    NodeWithTotalDistanceAndPrevious(n, getDistanceToNeighbour(n) + currentNode.totalDistance, currentNode)
                }
        }

    // Use this to Add a node to unvisited nodes only. Visited nodes don't have distances.
    private fun HashSet<NodeWithTotalDistanceAndPrevious>.addNodesIfCloser(nodes: Collection<NodeWithTotalDistanceAndPrevious>){
        nodes.forEach{ thisNode ->
            // If the same node is in, and known distance is bigger than current distance, remove it so it will be replaced
            // If known distance is smaller, adding thisNode will not do anything.
            // If no node exists, we can just add it.
            this.firstOrNull { it.node == thisNode.node }?.let{ foundNode ->
                if (foundNode.totalDistance > thisNode.totalDistance) {
                    remove(foundNode)
                }
            }
            add(thisNode)
        }
    }

    private fun HashSet<NodeWithTotalDistanceAndPrevious>.popClosest(): NodeWithTotalDistanceAndPrevious? =
        minByOrNull { it.totalDistance }.also{
            remove(it)
        }

    class RouteNotFoundException: IllegalStateException()

}