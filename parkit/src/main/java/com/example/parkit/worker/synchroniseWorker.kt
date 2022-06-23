package com.example.parkit.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.parkit.database.AppDatabase
import com.example.parkit.entity.rating
import com.example.parkit.retrofit.Endpoint

class synchroniseWorker(val appContext : Context, val workerParams : WorkerParameters)
    : CoroutineWorker(appContext, workerParams){
    override suspend fun doWork(): Result {

        return try{
            //get the not synched ratings
            val db = AppDatabase.buildDatabase(appContext);
            val ratings:List<rating>? = db?.getRatingDao()?.getNotSynchedRatings()
            if(ratings != null){
                for (r in ratings){
                    val response = Endpoint.createEndpoint().rateParking(r)
                    if(response.body()=="200"){
                        r.isSync = true
                        db?.getRatingDao()?.syncRate(r)

                    }else{
                        throw Exception()
                    }
                }
            }
            return Result.success()
        } catch (error: Throwable){
            Result.retry()
        }
    }
}