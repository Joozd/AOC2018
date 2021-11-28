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

/**
 * Find a repeating pattern at the end of a list, and return a list with that pattern
 * @param minRepeats: How often that pattern must repeat itself
 *  (if you want [1,2,2,3,1,2,2,3,1,2,2,3,...] to return [1,2,2,3] you should set this to higher than 2)
 * @return found repeating sequence, or 0 if no repeats found
 */
fun <T> List<T>.findPeriodOfGrowth(minRepeats: Int = 3, minimumLength: Int = 1): List<T>?{
    if (size < minimumLength * minRepeats) return null
    val sizeToCheck = size/minRepeats
    return (minimumLength..sizeToCheck).firstOrNull { currentLength ->
        val currentSequence = takeLast(currentLength)
        this.takeLast(currentLength * minRepeats) == currentSequence * minRepeats
    }?.let{
        this.takeLast(it)
    }
}

operator fun <T> List<T>.times(repeats: Int) = List(repeats) { this }.flatten()