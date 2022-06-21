package com.example.parkit.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class synchroniseWorker(appContext : Context, workerParams : WorkerParameters)
    : Worker(appContext, workerParams){
    override fun doWork(): Result {
        return Result.success()
    }
}