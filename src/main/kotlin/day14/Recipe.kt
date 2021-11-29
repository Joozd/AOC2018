package day14

import common.extensions.endsWith

class Recipe {
    private val list = listOf(3,7).toMutableList()
    private val elves = IntArray(2){ it }

    private fun addRecipes(){
        val newRecipe = elves.sumOf { list[it] }
        if (newRecipe >= 10) list.add(1)
        list.add(newRecipe % 10)

        increaseElves()
    }

    fun getRecipes(indices: IntRange): List<Int>{
        while(list.size < indices.last + 1){
            addRecipes()
        }
        return list.slice(indices)
    }

    fun getToTheLeft(searchList: List<Int>): Int{
        val length = searchList.size
        while(!list.endsWith(searchList) && !list.takeLast(length+1).dropLast(1).endsWith(searchList)){
            addRecipes()
        }
        return if (list.endsWith(searchList)) list.size - length else list.size - length - 1
    }

    override fun toString() = list.mapIndexed{ i, v -> if (i in elves) "[$v]" else v.toString() }.joinToString(" ")

    /**
     * Make all elves step forward 1+the current recipe value, wrapping around if larger than [list].
     */
    private fun increaseElves(){
        elves.indices.forEach{
            increaseElf(it)
        }
    }

    private fun increaseElf(elfNumber: Int){
        elves[elfNumber] = (elves[elfNumber] + 1 + list[elves[elfNumber]]) % list.size
    }
}