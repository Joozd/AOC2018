package common.dijkstra

/**
 * A Neighbour is any Node that can be reached from origin node without passing any other node,
 * together with the distance to that node FROM THE ORIGIN
 * If [distance] is null it means 0. (for start position).
 */
open class NodeWithDistance<T> (val node: Node<T>, val distance: T?): Node<T>, Comparable<NodeWithDistance<T>> where T: Number, T: Comparable<T>{
    override fun getNeighbours() = node.getNeighbours()

    override fun getDistanceToNeighbour(neighbour: Node<T>) = node.getDistanceToNeighbour(neighbour).addTo<T>(distance)

    override operator fun compareTo(other: NodeWithDistance<T>): Int =
        if (other.distance == null)
            if (distance == null) 0 else 1
        else
            distance?.compareTo(other.distance) ?: 1

    /**
     * Equals only checks if this is the same node, not the same distance!
     * If you want to compare those too, use [equalsAll]
     */
    override fun equals(other: Any?): Boolean {
        if (other is NodeWithDistance<*>)
            return other.node == node
        return if (other is Node<*>)
            other == node
        else false
    }

    /**
     * Checks if all is equal
     */
    fun equalsAll(other: NodeWithDistance<T>): Boolean =
        other.node == node
                && other.distance == distance

    override fun hashCode(): Int = node.hashCode()
}