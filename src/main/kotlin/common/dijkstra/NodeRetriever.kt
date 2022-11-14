package common.dijkstra

/**
 * Function to retrieve nodes from some place. Can invalidate nodes by making them null.
 */
fun interface NodeRetriever<T> where T: Number, T: Comparable<T>{
    operator fun get(node: Node<T>): Node<T>?
}