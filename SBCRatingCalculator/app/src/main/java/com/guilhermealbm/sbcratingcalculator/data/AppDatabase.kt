package com.guilhermealbm.sbcratingcalculator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating

@Database(entities = [PlayerRating::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerRatingDao() : PlayerRatingDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "database-name"
            ).build()
        }
    }

}