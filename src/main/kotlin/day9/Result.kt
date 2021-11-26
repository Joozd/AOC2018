package day9

class Result(val score: Long, val currentMarble: Marble){
    fun addScore(extraScore: Long): Result = Result(score + extraScore, currentMarble)

    override fun toString(): String = "Result: $score, currentMarble = ${currentMarble.value}"
}