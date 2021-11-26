package day8

class Node(rawInputData: List<Int>) {
    private val childNodes: List<Node> = parseChildNodes(rawInputData)
    private val metaData: List<Int> = getMetaData(rawInputData)
    private val length: Int = 2 + childNodes.sumOf{ it.length} + metaData.size

    fun getValue(): Int {
        return if (childNodes.isEmpty()) metaData.sum()
        else metaData.sumOf { childNodes.getOrNull(it - 1)?.getValue() ?: 0 }
    }



    fun totalMetaDataValue(): Int = metaData.sum() + childNodes.sumOf{ it.totalMetaDataValue() }


    private fun parseChildNodes(input: List<Int>): List<Node> {
        val foundNodes = ArrayList<Node>()
        var pointer = 2 // first node starts at pos 2 because of header
        val amountOfNodes = input[0]
        repeat(amountOfNodes){
            val nodeToAdd = Node(input.drop(pointer))
            foundNodes.add(nodeToAdd)
            pointer += nodeToAdd.length
        }
        return foundNodes
    }

    private fun getMetaData(input: List<Int>): List<Int>{
        val amountOfMetaData = input[1]
        val pointer = 2 + childNodes.sumOf { it.length}
        return input.slice(pointer until pointer + amountOfMetaData)
    }
}