package common.extensions

fun String.words() = this.split(" ")

//won't take comma separated numbers if they lack a whitespace
fun String.grabNumbers(): List<Long> = filter{ it in "0123456789- "}.words().filter {it.isNotBlank()}.map{it.toLong()}

fun String.grabInts(): List<Int> =
    this.replace("[^0-9-]".toRegex(), " ")
        .split(" ")
        .filter { it.isNotBlank()}
        .map {it.toInt()}