package day14

import common.Solution

class Day14: Solution {
    override val day = 14
    private val input = readInput().toInt()
    private val inputNumbers = readInput().map{ it.toString().toInt() }
    private val recipe = Recipe()

    override fun answer1(): Any =
        recipe.getRecipes(input..input+10).joinToString("")


    override fun answer2(): Any =
        recipe.getToTheLeft(inputNumbers)
}