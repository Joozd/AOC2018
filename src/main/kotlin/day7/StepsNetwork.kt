package day7

class StepsNetwork {
    private val stepsMap = mutableMapOf<Char, Step>()
    private val steps = stepsMap.values
    private val workers = Workers(5)

    var timer = 0
        private set

    operator fun get(step: Char) = stepsMap[step]

    fun fillMap(input: List<String>){
        input.map { listOf(it[5], it[36])}.flatten().distinct().forEach{
            stepsMap[it] = Step(it)
        }
        stepsMap.values.forEach {
            it.addRequirements(input, this)
        }
    }

    fun reset(){
        steps.forEach {
            it.reset()
        }
    }

    private fun getAvailableSteps() = steps.filter {it.canBeDone()}

    fun getNextStep() = getAvailableSteps().minByOrNull { it.id }

    fun doNextStep() =
         getNextStep()?.apply{
            completeStep()
        }

    fun tick(){
        finishCompletedWork()
        while (workers.workerAvailable(timer) && getNextStep() != null) {
            workers.assignWork(getNextStep()!!, timer)
        }
        timer = if (getNextStep() == null)
            nextJobDoneTime()?: timer
        else workers.nextWorkerAvailableTime()
    }

    fun allStepsCompleted() = steps.all { it.completed }

    private fun getWorkInProgress() = steps.filter { it.inProgress }

    private fun nextJobDoneTime() = getWorkInProgress().minOfOrNull { it.timeCompleted }

    private fun finishCompletedWork(){
        steps.forEach {
            it.tryToFinish(timer)
        }
    }


}