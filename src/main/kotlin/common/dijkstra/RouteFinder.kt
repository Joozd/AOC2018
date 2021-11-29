package common.dijkstra

/**
 * RouteFinder can be used to find a route through a network using a Dijkstra algorithm.
 * @param nodeRetriever: A function to retrieve nodes that are generated by [Node.getNeighbours]
 * in case the nodes themselves do not know the entire network but can generate what neighbours they have.
 */
class RouteFinder<T>(private val nodeRetriever: NodeRetriever<T>?) where T: Number, T: Comparable<T>{
    fun findRoute(start: Node<T>, end: Node<T>) = findRouteToNodesWithDistances(start, end)?.map {it.node}

    /**
     * findRouteToNodesWithDistances will return the shortest route from [start] to [end] or null if no route found.
     * @return list of Nodes wrapped in
     */
    private fun findRouteToNodesWithDistances(start: Node<T>, end: Node<T>): List<NodeWithDistanceAndOrigin<T>>? {
        println("Looking for route:\nStart: $start\nEnd  : $end")
        val startNode = NodeWithDistanceAndOrigin(find(start), null, null)
        val shortesRouteFoundNodes = mutableSetOf(startNode)
        val knownNodes = startNode.findNeighbours().map {
            NodeWithDistanceAndOrigin.make(it, startNode)
        }.toHashSet()
        while (knownNodes.isNotEmpty()) {
            processNextNode(knownNodes, shortesRouteFoundNodes).let {
                if (it == end)
                    return getRouteToNodesWithDistances(it)
            }
        }
        return null
    }

    /**
     * Distance to
     */
    fun findDistance(start: Node<T>, end: Node<T>) =
        findRouteToNodesWithDistances(start, end)?.last()?.distance


    private fun processNextNode(
        knownNodes: HashSet<NodeWithDistanceAndOrigin<T>>,
        shortesRouteFoundNodes: MutableSet<NodeWithDistanceAndOrigin<T>>
    ): NodeWithDistanceAndOrigin<T> {
        val currentNode = popClosestNode(knownNodes)
        shortesRouteFoundNodes.add(currentNode)
        currentNode.findNeighbours().forEach { neighbour ->
            knownNodes.addOrReplaceIfSmaller(NodeWithDistanceAndOrigin.make(neighbour, currentNode))
        }
        return currentNode
    }

    private fun find(node: Node<T>): Node<T> = nodeRetriever?.get(node) ?: node

    private fun Node<T>.findNeighbours() = getNeighbours().map { find(it) }

    private fun getRouteToNodesWithDistances(endPoint: NodeWithDistanceAndOrigin<T>): List<NodeWithDistanceAndOrigin<T>> {
        val backtrack = ArrayList(listOf(endPoint))
        var previous = endPoint.previousNode
        while (previous != null){
            backtrack.add(previous)
            previous = previous.previousNode
        }
        return backtrack.reversed()
    }


    /**
     * Gets closest node from a mutable list of [NodeWithDistance]
     */
    private fun popClosestNode(nodes: MutableSet<NodeWithDistanceAndOrigin<T>>): NodeWithDistanceAndOrigin<T>{
        val closest = nodes.minByOrNull { it.distance!! } ?: throw (NoSuchElementException("Can't pop an empty list"))
        nodes.remove(closest)
        return closest
    }

    fun interface NodeRetriever<T> where T: Number, T: Comparable<T>{
        operator fun get(node: Node<T>): Node<T>
    }

    /**
     * @param node: this Node
     * @param distance: Distance from start to this Node
     * @param previousNode: Previous Node
     */
    private class NodeWithDistanceAndOrigin<T>(
        node: Node<T>,
        distance: T?,
        val previousNode: NodeWithDistanceAndOrigin<T>?
    ): NodeWithDistance<T>(node, distance) where T: Number, T: Comparable<T>
    {
        companion object{
            fun <T> make(
                node: Node<T>,
                origin: NodeWithDistanceAndOrigin<T>
            ): NodeWithDistanceAndOrigin<T> where T: Number, T: Comparable<T>{
                return NodeWithDistanceAndOrigin(node, origin.getDistanceToNeighbour(node), origin)
            }
        }
    }
}