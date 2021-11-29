package common.dijkstra



/**
 * I wanted to go with operator but that would remove compiler type safety so no
 * If [other] is null it counts as zero.
 */
internal fun <T> Number.addTo(other: Number?): T where T: Number, T:Comparable<T> {
    require (other == null || this::class == other::class) { "Only add same class please"}
    @Suppress("UNCHECKED_CAST")
    return (when(this){
        is Int -> this + (other ?: 0) as Int
        is Long -> this + (other ?: 0L) as Long
        is Short -> this + (other ?: 0.toShort()) as Short
        is Float -> this + (other ?: 0.toFloat()) as Float
        is Double -> this + (other ?: 0.toDouble()) as Double
        is Byte -> this + (other ?: 0.toByte()) as Byte
        else -> error ("Not supported yet, please fix that here")
    }) as T
}

internal fun Number.zero() = when(this){
    is Int -> 0
    is Long -> 0L
    is Short -> 0.toShort()
    is Float -> 0.toFloat()
    is Double -> 0.toDouble()
    is Byte -> 0.toByte()
    else -> error ("Not supported yet, please fix that here")
}

/**
 * Replaces an element of this set with a new element. Doesn't keep order.
 */
internal fun <T> HashSet<T>.addOrReplace(element: T){
    this.remove(element)
    this.add(element)
}

internal fun <T> HashSet<T>.addOrReplaceIf(element: T, predicate: (T) -> Boolean) {
    if (predicate(element))
        addOrReplace(element)
}

/**
 * Replaces element in this HashSet by [element] if it is smaller.
 */
internal fun <T: Comparable<T>> HashSet<T>.addOrReplaceIfSmaller(element: T) {
    addOrReplaceIf(element){
        firstOrNull { it == element }?.let{
            it > element
        } ?: true
    }
}
