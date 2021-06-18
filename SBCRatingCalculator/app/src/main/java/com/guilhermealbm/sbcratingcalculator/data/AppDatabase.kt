package com.guilhermealbm.sbcratingcalculator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating

@Database(entities = [PlayerRating::class], version = 1)
abstract class AppDatabase : RoomDatabase()