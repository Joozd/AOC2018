package day10

import common.extensions.grabNumbers

class Star(x: Int, y: Int, private val vx: Int, private val vy: Int) {
    var x = x
        private set

    var y = y
        private set

    fun tick(){
        x += vx
        y += vy
    }
    fun copy() = Star(x,y,vx,vy)

    companion object{
        fun of(line: String): Star{
            val numbers = line.grabNumbers().map {it.toInt()}
            return Star(numbers[0], numbers[1], numbers[2], numbers[3])
        }
    }
}