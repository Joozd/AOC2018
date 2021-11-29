package common.dijkstra

/**
 * A Node to be used in a network for Dijkstra-ing my way to another Node.
 */
interface Node<T> where T: Number, T: Comparable<T> {
    /**
     * Get a list of all [NodeWithDistance]s
     * A neighbour is any Node that can be reached from this node without passing any other node,
     *  together with the distance to that node
     */
    fun getNeighbours(): List<Node<T>>

    fun getDistanceToNeighbour(neighbour: Node<T>): T


}