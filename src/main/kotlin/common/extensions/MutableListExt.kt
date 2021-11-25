package common.extensions

fun <T> MutableList<T>.addNotNull(element: T?){
    if (element != null) add(element)
}