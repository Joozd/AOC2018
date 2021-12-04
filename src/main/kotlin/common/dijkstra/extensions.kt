package common.dijkstra

/**
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

internal fun <T> MutableList<T>.addOrReplace(element: T){
    val i = indexOf(element)
    if (i == -1) add(element)
    else this[i] = element
}

internal inline fun <T> MutableList<T>.addOrReplaceIf(element: T, predicate: (T) -> Boolean) {
    if (predicate(element))
        addOrReplace(element)
}

/**
 * Replaces element in this HashSet by [element] if it is smaller.
 */
internal fun <T: Comparable<T>> MutableList<T>.addOrReplaceIfSmaller(element: T) {
    addOrReplaceIf(element){
        firstOrNull { it == element }?.let{
            it > element
        } ?: true
    }
}

internal fun Int.abs() = if (this > 0) this else this * -1