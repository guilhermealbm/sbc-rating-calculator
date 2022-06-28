package com.guilhermealbm.sbcratingcalculator.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import com.guilhermealbm.sbcratingcalculator.viewmodels.SBCRatingCalculatorViewModel

@Composable
fun SBCRatingCalculator(viewModel: SBCRatingCalculatorViewModel = viewModel()) {
    val playerRatingsList: List<PlayerRating>? by viewModel.playersByRating.observeAsState()
    SBCRatingCalculatorList(
        playerRatingsList,
        onRemovePlayer = { viewModel.updatePlayerInList(it, false) },
        onAddPlayer = { viewModel.updatePlayerInList(it, true) }
    )
}

@Composable
fun SBCRatingCalculatorList(
    playerRatingsList: List<PlayerRating>?,
    onRemovePlayer: (PlayerRating) -> Unit,
    onAddPlayer: (PlayerRating) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        playerRatingsList?.let {
            items(it) { playerRating ->
                PlayerRatingSelector(
                    playerRating = playerRating,
                    onRemovePlayer = onRemovePlayer,
                    onAddPlayer = onAddPlayer
                )
            }
        }
    }
}

@Composable
private fun PlayerRatingSelector(
    playerRating: PlayerRating,
    onRemovePlayer: (PlayerRating) -> Unit,
    onAddPlayer: (PlayerRating) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onRemovePlayer(playerRating) }) {
            Icon(Icons.Default.Remove, contentDescription = null)
        }
        Text(text = playerRating.players.toString())
        IconButton(onClick =  { onAddPlayer(playerRating) }) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }
}

@Preview(name = "SBCRatingCalculator")
@Composable
private fun SBCRatingCalculatorPreview() {
    val playerRatings = listOf(
        PlayerRating(80, 1),
        PlayerRating(81, 2),
        PlayerRating(82, 3),
        PlayerRating(83, 5),
        PlayerRating(84, 0)
    )
    SBCRatingCalculatorList(playerRatings, {}, {})
}

@Preview(name = "PlayerRatingSelector")
@Composable
private fun PlayerRatingSelectorPreview() {
    val playerRatings = listOf(
        PlayerRating(80, 1),
        PlayerRating(81, 2),
        PlayerRating(82, 3),
        PlayerRating(83, 5),
        PlayerRating(84, 0)
    )
    PlayerRatingSelector(playerRatings.first(), {}, {})
}