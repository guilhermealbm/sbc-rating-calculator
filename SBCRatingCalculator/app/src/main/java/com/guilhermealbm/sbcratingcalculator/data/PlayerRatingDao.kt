package com.guilhermealbm.sbcratingcalculator.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerRatingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(playerRating: PlayerRating)

    @Query("SELECT * FROM playerRating")
    fun load(): Flow<List<PlayerRating>>
}
