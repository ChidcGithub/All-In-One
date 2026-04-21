package com.allinone.feature.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

/**
 * Demo worker that represents the second step in a work chain.
 */
class ChainedDemoWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val inputData = inputData.getString("previous_result") ?: "No input"
        val outputData = workDataOf(
            "result" to "Chained work completed. Input: $inputData"
        )
        return Result.success(outputData)
    }
}
