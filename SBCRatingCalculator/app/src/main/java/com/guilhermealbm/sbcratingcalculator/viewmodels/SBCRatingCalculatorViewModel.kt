package com.guilhermealbm.sbcratingcalculator.viewmodels

import androidx.lifecycle.*
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import com.guilhermealbm.sbcratingcalculator.model.createRatings
import com.guilhermealbm.sbcratingcalculator.repository.PlayerRatingRepository
import com.guilhermealbm.sbcratingcalculator.util.MISSING_PLAYERS
import com.guilhermealbm.sbcratingcalculator.util.TOO_MANY_PLAYERS
import com.guilhermealbm.sbcratingcalculator.util.calculateSquadRating
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SBCRatingCalculatorViewModel @Inject constructor (
    private val playerRatingRepository: PlayerRatingRepository
) : ViewModel() {

    val playersByRating = playerRatingRepository.getPlayersRating().asLiveData()

    fun savePlayersRatingDb(playerRatings: List<PlayerRating>) {
        viewModelScope.launch {
            playerRatingRepository.savePlayerRatings(playerRatings)
        }
    }

    fun getSquadRating(players: List<PlayerRating>) : Int {
        val playersNum = players.map { p -> p.players }.sum()
        return when {
            playersNum < 11 -> MISSING_PLAYERS
            playersNum == 11 -> calculateSquadRating(players)
            else -> TOO_MANY_PLAYERS
        }
    }

    fun clearData() = savePlayersRatingDb(createRatings().toList())

}