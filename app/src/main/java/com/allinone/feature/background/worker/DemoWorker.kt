package com.allinone.feature.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

/**
 * Demo worker that performs a simulated background task.
 * Returns progress and result data to demonstrate WorkManager capabilities.
 */
class DemoWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val taskName = inputData.getString(KEY_TASK_NAME) ?: "Default Task"

        // Simulate work with progress reporting
        for (i in 1..5) {
            if (isStopped) return Result.failure()
            setProgress(workDataOf("progress" to (i * 20)))
            Thread.sleep(500)
        }

        val outputData = workDataOf(
            "result" to "$taskName completed successfully",
            "timestamp" to System.currentTimeMillis()
        )

        return Result.success(outputData)
    }

    companion object {
        const val KEY_TASK_NAME = "task_name"
    }
}
