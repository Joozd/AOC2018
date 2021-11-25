package common.extensions

fun <T> List<T>.countIndexed(predicate: (Int, T) -> Boolean): Int {
    var counter = 0
    for (i in this.indices){
        if (predicate(i, this[i])) counter++
    }
    return counter
}

/**
 * Returns the item that occurs the most in this list. If tied, the first one encountered.
 * Null if nothing found.
 */
fun <T> List<T>.getMostOccurringItem(): T?{
    return this.toSet().maxByOrNull { element -> this.count {it == element} }
}

fun List<*>.getMostOccurringFrequency(): Int{
    return getMostOccurringItem()?.let{ element ->
        count{ it == element }
    } ?: 0
}