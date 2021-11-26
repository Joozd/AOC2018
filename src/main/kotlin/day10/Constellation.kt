package day10

class Constellation(starLines: List<String>) {
    private var stars = starLines.map { Star.of(it) }
    private var savedState: List<Star> = copyStars()

    var ticks: Int = 0
        private set

    private var savedStateTicks = 0

    override fun toString() = dumpStarImage()

    fun height(): Int = stars.maxOf { it.y} - stars.minOf { it.y}
    private fun width(): Int = stars.maxOf { it.x} - stars.minOf { it.x}

    private fun offsetX() = stars.minOf { it.x }
    private fun offsetY() = stars.minOf { it.y }

    fun saveState() {
        savedState = copyStars()
        savedStateTicks = ticks
    }

    fun restoreState(){
        stars = savedState
        ticks = savedStateTicks
    }

    fun tick(){
        stars.forEach { it.tick() }
        ticks++
    }

    private fun copyStars() = stars.map { it.copy() }

    private fun dumpStarImage(): String{
        val offsetX = offsetX()
        val offsetY = offsetY()

        val result = Array(height()+1){ CharArray(width()+1) { ' ' } }

        stars.forEach{
            result[it.y - offsetY][it.x - offsetX] = '#'
        }
        return result.joinToString("\n"){ it.joinToString("")}
    }
}