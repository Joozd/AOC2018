package day7

class Step(val id: Char) {
    var completed = false
        private set
    var inProgress = false
        private set
    var timeCompleted: Int = 0
        private set
    private var reqs = ArrayList<Step>()

    fun addRequirements(requirements: List<String>, steps: StepsNetwork){
        requirements.filter { it.endsWith("$id can begin.")}.forEach{
            reqs.add(steps[it[5]]!!)
        }
    }

    fun reset(){
        completed = false
        inProgress = false
        timeCompleted = 0
    }

    fun canBeDone() = !completed && !inProgress && reqs.all { it.completed}

    fun completeStep(){
        completed = true
    }

    fun tryToFinish(time: Int){
        if (inProgress && timeCompleted <= time){
            completed = true
            inProgress = false
        }
    }

    /**
     * Starts work, meaning this cannot be started again and is not finished yet
     * @return the amount of time this will take
     */
    fun startWork(time: Int) {
        inProgress = true
        timeCompleted =  id.code - 'A'.code + 61 + time
    }
}