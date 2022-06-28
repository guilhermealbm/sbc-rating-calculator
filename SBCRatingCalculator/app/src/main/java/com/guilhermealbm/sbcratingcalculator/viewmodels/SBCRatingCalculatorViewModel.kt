package com.guilhermealbm.sbcratingcalculator.viewmodels

import androidx.lifecycle.*
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import com.guilhermealbm.sbcratingcalculator.model.createRatings
import com.guilhermealbm.sbcratingcalculator.repository.PlayerRatingRepository
import com.guilhermealbm.sbcratingcalculator.util.MISSING_PLAYERS
import com.guilhermealbm.sbcratingcalculator.util.TOO_MANY_PLAYERS
import com.guilhermealbm.sbcratingcalculator.util.calculateSquadRating
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SBCRatingCalculatorViewModel @Inject constructor (
    private val playerRatingRepository: PlayerRatingRepository
) : ViewModel() {

    private val _playersByRating = MutableLiveData<List<PlayerRating>>()
    val playersByRating: LiveData<List<PlayerRating>?> = _playersByRating

    private val _totalPlayers = MutableLiveData<Int>()
    val totalPlayers: LiveData<Int> = _totalPlayers

    init {
        viewModelScope.launch {
            _playersByRating.value = playerRatingRepository.getPlayersRating().first()
            _totalPlayers.value = _playersByRating.value?.sumOf { it.players }
        }
    }

    fun savePlayersRatingDb(playerRatings: List<PlayerRating>) {
        viewModelScope.launch {
            playerRatingRepository.savePlayerRatings(playerRatings)
        }
    }

    fun updatePlayerInList(playerRating: PlayerRating, add: Boolean = true) {
        val index = _playersByRating.value?.indexOf(playerRating) ?: 0

        if (!add && playerRating.players == 0)
            return

        val newPlayerRating: PlayerRating = if (add)
            PlayerRating(playerRating.rating, playerRating.players + 1)
        else
            PlayerRating(playerRating.rating, playerRating.players - 1)

        _totalPlayers.value = if (add)
             _totalPlayers.value?.plus(1)
            else
            _totalPlayers.value?.minus(1)

        val newList = _playersByRating.value?.toMutableList()
        newList?.set(index, newPlayerRating)
        _playersByRating.value = newList
    }

    fun getSquadRating(players: List<PlayerRating>) : Int {
        val playersNum = players.sumOf { it.players }
        return when {
            playersNum < 11 -> MISSING_PLAYERS
            playersNum == 11 -> calculateSquadRating(players)
            else -> TOO_MANY_PLAYERS
        }
    }

    fun clearData() = savePlayersRatingDb(createRatings().toList())

}