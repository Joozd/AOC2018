package common.dijkstra

class NodeWithTotalDistanceAndPrevious (
    val node: Node<Int>,
    val totalDistance: Int,
    val previousNode: NodeWithTotalDistanceAndPrevious? // null for first node
){
    //This is for making sure only one of each node is stored in a HashSet.
    override fun equals(other: Any?): Boolean =
        other is NodeWithTotalDistanceAndPrevious && other.node == node

    override fun hashCode(): Int = node.hashCode()

    override fun toString() = "$node from ${previousNode?.node}, dist = $totalDistance"

}