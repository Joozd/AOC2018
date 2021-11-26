package common.extensions

fun String.words() = this.split(" ")

fun String.grabNumbers(): List<Long> = filter{ it in "0123456789- "}.words().filter {it.isNotBlank()}.map{it.toLong()}