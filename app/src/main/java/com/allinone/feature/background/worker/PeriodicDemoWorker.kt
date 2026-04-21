package com.allinone.feature.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

/**
 * Demo worker for periodic background tasks.
 */
class PeriodicDemoWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // Simulate periodic work
        val outputData = workDataOf(
            "message" to "Periodic work executed at ${System.currentTimeMillis()}"
        )
        return Result.success(outputData)
    }
}
