package day21

import common.Device
import common.Solution

/*
What is the lowest non-negative integer value for register 0 that causes the program to halt after executing the fewest instructions?
 */
class Day21: Solution {
    override val day = 21

    private val input = inputLines()

    override fun answer1() = with(Device()){
        load(input)

        //Run the program until the compare in line 28
        while (get(2) !=28){
            tick()
        }
        get(5) //return value of reg5, which is what reg0 gets compared to.
    }

    //4656685 is too low
    override fun answer2(): Any = with(Device()) {
        val haltingValues = LinkedHashSet<Int>()
        load(input)

        //Run the program until the compare in line 28
        while (true){
            while (get(2) != 28) {
                tick()
            }
            if (!haltingValues.add(get(5))) return@with haltingValues.last()
            println("Found ${haltingValues.size}")
            tick()
        }
    }
}