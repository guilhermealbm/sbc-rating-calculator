package com.guilhermealbm.sbcratingcalculator.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.guilhermealbm.sbcratingcalculator.data.AppDatabase
import com.guilhermealbm.sbcratingcalculator.model.createRatings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val database = AppDatabase.getInstance(applicationContext)
        database.playerRatingDao().saveAll(createRatings().toList())

        Result.success()
    }
}