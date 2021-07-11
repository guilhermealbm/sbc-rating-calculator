package com.guilhermealbm.sbcratingcalculator.viewmodels

import androidx.lifecycle.*
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import com.guilhermealbm.sbcratingcalculator.repository.PlayerRatingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SBCRatingCalculatorViewModel @Inject constructor (
    private val playerRatingRepository: PlayerRatingRepository
) : ViewModel() {

    val playersByRating = playerRatingRepository.getPlayersRating().asLiveData()

    fun savePlayerRatingDb(playerRating: PlayerRating) {
        viewModelScope.launch {
            playerRatingRepository.savePlayerRating(playerRating.rating, playerRating.players)
        }
    }
}