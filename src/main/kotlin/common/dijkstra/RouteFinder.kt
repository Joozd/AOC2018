package common.dijkstra

import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList

/**
 * RouteFinder can be used to find a route through a network using a Dijkstra algorithm.
 * @param nodeRetriever: A function to retrieve nodes that are generated by [Node.getNeighbours]
 * in case the nodes themselves do not know the entire network but can generate what neighbours they have.
 */
class RouteFinder<T>(private var nodeRetriever: NodeRetriever<T>? = null) where T: Number, T: Comparable<T>{
    fun findRoute(start: Node<T>, end: Node<T>) = findRouteToNodesWithDistances(find(start)?: start, end)?.map {it.node}

    fun findRoute(start: Node<T>, end: Node<T>, overrideNodeRetriever: NodeRetriever<T>): List<Node<T>>? {
        val oldNodeRetriever = nodeRetriever
        nodeRetriever = overrideNodeRetriever
        val result = findRouteToNodesWithDistances(start, end)?.map {it.node}
        nodeRetriever = oldNodeRetriever
        return result
    }


    /**
     * findRouteToNodesWithDistances will return the shortest route from [start] to [end] or null if no route found.
     * Start should be an actual node, it will not be looked up.
     * @return list of Nodes wrapped in
     */
    private fun findRouteToNodesWithDistances(start: Node<T>, end: Node<T>): List<NodeWithDistanceAndOrigin<T>>? {
        val startNode = NodeWithDistanceAndOrigin(start, null, null)
        val finishedNodes = HashSet<Node<T>>().apply { add(start) }
        val foundNodes = HashSet(startNode.findNeighbours().map {
            NodeWithDistanceAndOrigin.make(it, startNode)
        })
        while (foundNodes.isNotEmpty()) {
            processNextNode(foundNodes, finishedNodes, end).let {
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

    fun findDistance(nodesList: List<Node<T>>): T?{
        if (nodesList.size < 2) return null
        val nodes = LinkedList(nodesList)
        var currentLength = nodes.removeFirst().getDistanceToNeighbour(nodes.first())
        while (nodes.size >= 2){
            currentLength = currentLength.addTo(nodes.removeFirst().getDistanceToNeighbour(nodes.first()))
        }
        return currentLength
    }


    private fun processNextNode(
        foundNodes: MutableSet<NodeWithDistanceAndOrigin<T>>,
        finishedNodes: MutableSet<Node<T>>,
        end: Node<T>
    ): NodeWithDistanceAndOrigin<T> {
        val currentNode = popClosestNode(foundNodes, end)
        //println("Looking at ${currentNode.node}")
        //println("known routes: ${finishedNodes.map { if (it is NodeWithDistance) it.node as Coordinate else it as Coordinate}.sorted() }")
        //println("found nodes: ${foundNodes.map { it.node }}\n")
        replaceFoundNodesIfSmaller(currentNode, finishedNodes, foundNodes)
        finishedNodes.add(currentNode)
        return currentNode
    }

    private fun replaceFoundNodesIfSmaller(
        currentNode: NodeWithDistanceAndOrigin<T>,
        finishedNodes: MutableCollection<Node<T>>,
        foundNodes: MutableSet<NodeWithDistanceAndOrigin<T>>
    ) {
        currentNode.findNeighbours().filter { it !in getNodesFrom(finishedNodes) }.forEach { neighbour ->
            //println("found neighbour $neighbour (and it is not in ${getNodesFrom(finishedNodes)})")
            foundNodes.addOrReplaceIfSmaller(NodeWithDistanceAndOrigin.make(neighbour, currentNode))
        }
    }

    private fun find(node: Node<T>): Node<T>? = if (nodeRetriever != null) nodeRetriever!![node] else node

    private fun Node<T>.findNeighbours() = getNeighbours().mapNotNull { find(it) }

    private fun getRouteToNodesWithDistances(endPoint: NodeWithDistanceAndOrigin<T>): List<NodeWithDistanceAndOrigin<T>> {
        val backtrack = ArrayList(listOf(endPoint))
        var previous = endPoint.previousNode
        while (previous != null){
            backtrack.add(previous)
            previous = previous.previousNode
        }
        return backtrack.reversed()
    }

    private fun getNodesFrom(nodes: Collection<Node<T>>) = nodes.map { if (it is NodeWithDistance) it.node else it }


    /**
     * Gets closest node from a mutable list of [NodeWithDistance]
     */
    private fun popClosestNode(nodes: MutableCollection<NodeWithDistanceAndOrigin<T>>, end: Node<T>): NodeWithDistanceAndOrigin<T>{
        val closest = getClosestNode(nodes, end)
        nodes.remove(closest)
        return closest
    }

    /**
     * Gets closest
     */
    private fun getClosestNode(nodes: Collection<NodeWithDistanceAndOrigin<T>>, end: Node<T>): NodeWithDistanceAndOrigin<T> =
        if (end is GridNode) nodes.minByOrNull { it.distance as Int + getManhatanDistance(it.node as GridNode, end) } ?: throw (NoSuchElementException("Can't pop an empty list"))
        else nodes.minByOrNull { it.distance!! } ?: throw (NoSuchElementException("Can't pop an empty list"))

    private fun getManhatanDistance(p1: GridNode, p2: GridNode): Int =
        (p1.x - p2.x).abs() + (p1.y - p2.y).abs()




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