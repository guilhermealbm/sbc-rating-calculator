package com.guilhermealbm.sbcratingcalculator.repository

import com.guilhermealbm.sbcratingcalculator.data.PlayerRatingDao
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRatingRepository @Inject constructor(private val playerRatingDao: PlayerRatingDao) {

    fun getPlayersRating() = playerRatingDao.load()

    suspend fun savePlayerRating(rating: Int, players: Int) = playerRatingDao.save(playerRating = PlayerRating(rating, players))

}
