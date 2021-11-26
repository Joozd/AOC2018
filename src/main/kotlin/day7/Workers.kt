package day7

class Workers(amount: Int) {
    private val workers = Array(amount) { Worker() }

    private fun getAvailableWorker(time: Int) = workers.firstOrNull{ it.isAvailable(time) }

    fun workerAvailable(time: Int) = getAvailableWorker(time) != null

    fun assignWork(work: Step, time: Int){
        getAvailableWorker(time)!!.startWork(work, time)
    }

    fun nextWorkerAvailableTime() = workers.minOf { it.timeReady }

    private class Worker {
        var timeReady = 0
            private set

        fun isAvailable(time: Int) = time >= timeReady

        fun startWork (work: Step, time: Int){
            work.startWork(time)
            timeReady = work.timeCompleted
        }
    }
}