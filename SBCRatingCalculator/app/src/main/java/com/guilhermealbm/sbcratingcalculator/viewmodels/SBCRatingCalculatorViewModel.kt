package com.guilhermealbm.sbcratingcalculator.viewmodels

import androidx.lifecycle.*
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import com.guilhermealbm.sbcratingcalculator.model.createRatings
import com.guilhermealbm.sbcratingcalculator.repository.PlayerRatingRepository
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

    private val _teamRating = MutableLiveData<Int?>()
    val teamRating: LiveData<Int?> = _teamRating

    init {
        viewModelScope.launch {
            _playersByRating.value = playerRatingRepository.getPlayersRating().first()
            if (_playersByRating.value?.isEmpty() == true)
                _playersByRating.value = createRatings().toList()
            _totalPlayers.value = _playersByRating.value?.sumOf { it.players }
            if (_totalPlayers.value == 11) {
                _playersByRating.value?.let {
                    _teamRating.value = calculateSquadRating(it)
                }
            }
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

        if (_totalPlayers.value == 11) {
            _playersByRating.value?.let {
                _teamRating.value = calculateSquadRating(it)
            }
        } else {
            _teamRating.value = null
        }

    }

    fun clearData() = savePlayersRatingDb(createRatings().toList())

}