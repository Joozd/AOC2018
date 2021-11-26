package day7

import common.Solution
import common.extensions.words

class Day7: Solution {
    override val day = 7
    private val input = inputLines()
    private val stepsNetwork = StepsNetwork()


    override fun answer1(): CharSequence {
        stepsNetwork.fillMap(input)

        val result = StringBuilder(26)
        while (!stepsNetwork.allStepsCompleted())
            result.append(stepsNetwork.doNextStep()!!.id)

        return result
    }

    override fun answer2(): Int {
        stepsNetwork.reset()
        while (!stepsNetwork.allStepsCompleted()){
            stepsNetwork.tick()
        }
        return stepsNetwork.timer


    }


    /**
     * Fill map with steps and add the requirements
     */

}

//Step C must be finished before step A can begin.
//01234567890123456789012345678901234567890123456789
//00000000001111111111222222222233333333334444444444