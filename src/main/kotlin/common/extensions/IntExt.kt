package common.extensions

fun Int.abs() = if (this >= 0) this else this * -1

fun Int.increaseWithinBounds(min: Int, max: Int): Int = if (this + 1 > max) min else this + 1

fun Int.toBoolean() = this != 0