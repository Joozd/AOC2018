package day9

class Marble(val value: Long) {
    private var left: Marble = this
    private var right: Marble = this

    fun place(currentMarble: Marble): Result {
        return if (value %23 == 0L){
            val popped = popSevenLeft(currentMarble)
            popped.addScore(value)
        } else {
            placeAfter(currentMarble.right)
            Result(0, this)
        }
    }

    private fun placeAfter(leftNeighbour: Marble){
        val rightNeighbour = leftNeighbour.right
        connectLeft(leftNeighbour)
        connectRight(rightNeighbour)
    }

    private fun connectLeft(leftNeighbour: Marble){
        left = leftNeighbour
        leftNeighbour.right = this
    }

    private fun connectRight(rightNeighbour: Marble){
        right = rightNeighbour
        rightNeighbour.left = this
    }

    private fun popSevenLeft(currentMarble: Marble): Result{
        var m = currentMarble
        repeat(7){
            m = m.left
        }
        m.left.connectRight(m.right)
        return Result(m.value, m.right)
    }
}