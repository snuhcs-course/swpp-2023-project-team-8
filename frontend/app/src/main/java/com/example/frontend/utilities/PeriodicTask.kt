package com.example.frontend.utilities

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/*
 * Run a periodic task
 */
class PeriodicTask(
    private val command: Runnable,
    private val intervalInMinutes: Long = 5,
) {

    fun execute() {
        Log.i("PeriodicTask", "execute")

        val service = Executors.newSingleThreadScheduledExecutor()
        val handler = Handler(Looper.getMainLooper())
        service.scheduleAtFixedRate(
            handler.run { command },
            0, intervalInMinutes, TimeUnit.MINUTES
        )
    }
}

