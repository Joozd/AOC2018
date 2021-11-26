package day8

import common.Solution
import common.extensions.words

class Day8: Solution {
    override val day = 8
    private val input = inputNumbers()

    private lateinit var topNode: Node

    override fun answer1(): Int {
        topNode = Node(input)
        return topNode.totalMetaDataValue()
    }
    override fun answer2() = topNode.getValue()
}