package common

import java.time.Duration
import java.time.Instant

class Timer {
    private var startTime: Instant? = null

    fun start(){
        startTime = Instant.now()
    }

    fun lap(): Duration {
        val currentTime = Instant.now()
        return Duration.between(startTime, currentTime)
    }

    fun stop(): Duration{
        val elapsed = lap()
        startTime = null
        return elapsed
    }
}