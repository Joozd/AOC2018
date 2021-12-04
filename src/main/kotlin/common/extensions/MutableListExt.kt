package common.extensions

fun <T> MutableList<T>.addNotNull(element: T?): T?{
    if (element != null) add(element)
    return element
}