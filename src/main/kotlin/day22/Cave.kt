package day22

import common.Coordinate

//target is needed because that has a different danger level
class Cave(private val depth: Int, private val target: Coordinate) {
    private val caveSystem = HashMap<Coordinate, Region>().apply{
        put(target, Region(target, depth % MODULO, this@Cave))
    }

    fun riskLevel() = caveSystem.values.sumOf { it.dangerLevel }

    operator fun set(coordinate: Coordinate, region: Region){
        caveSystem[coordinate] = region
    }

    operator fun get(coordinate: Coordinate): Region =
        caveSystem[coordinate] ?: calculateAndInsertRegion(coordinate)

    // calculates a region, inserts it in [caveSystem] and returns it.
    private fun calculateAndInsertRegion(coordinate: Coordinate): Region =
        Region(coordinate, getErosionLevel(coordinate), this).also{
            caveSystem[it] = it
        }

    private fun getErosionLevel(coordinate: Coordinate): Int{
        return (when{
            coordinate.x == 0 -> coordinate.y * Y_MULTIPLIER                    // this includes 0 for 0,0
            coordinate.y == 0 -> coordinate.x * X_MULTIPLIER
            else -> {
                val n = this[coordinate.north()]
                val w = this[coordinate.west()]
                n.erosionLevel * w.erosionLevel
            }
        } + depth) % MODULO
    }







    companion object{
        private const val X_MULTIPLIER = 16807 // coordinates at y=0 get level it.x * X_MULTIPLIER
        private const val Y_MULTIPLIER = 48271 // coordinates at x=0 get level it.y * Y_MULTIPLIER
        private const val MODULO = 20183
    }
}