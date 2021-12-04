package common

open class CoordinateWithChar(x: Int, y: Int, open val c: Char): Coordinate(x,y) {
    companion object {
        fun fromListOfStrings(los: List<String>): List<CoordinateWithChar> =
            los.mapIndexed { y, row ->
                row.mapIndexedNotNull { x, c ->
                    CoordinateWithChar(x, y, c)
                }
            }.flatten()

        fun plotMap(map: List<CoordinateWithChar>): String{
            val maxY = map.maxOf { it.y } + 2
            val maxX = map.maxOf { it.x } + 2
            val plot = Array(maxY){ CharArray(maxX) {'#'}}
            map.forEach{
                plot[it.y][it.x] = it.c
            }
            return plot.joinToString("\n"){ it.joinToString("") }
        }
    }
}