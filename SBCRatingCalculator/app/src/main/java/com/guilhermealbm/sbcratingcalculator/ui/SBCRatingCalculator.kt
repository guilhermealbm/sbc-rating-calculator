package com.guilhermealbm.sbcratingcalculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import com.guilhermealbm.sbcratingcalculator.viewmodels.SBCRatingCalculatorViewModel

@Composable
fun SBCRatingCalculator(viewModel: SBCRatingCalculatorViewModel = viewModel()) {
    val playerRatingsList: List<PlayerRating>? by viewModel.playersByRating.observeAsState()
    val totalPlayers: Int? by viewModel.totalPlayers.observeAsState()
    SBCRatingCalculatorList(
        playerRatingsList,
        totalPlayers,
        onRemovePlayer = { viewModel.updatePlayerInList(it, false) },
        onAddPlayer = { viewModel.updatePlayerInList(it, true) }
    )
}

@Composable
fun SBCRatingCalculatorList(
    playerRatingsList: List<PlayerRating>?,
    totalPlayers: Int?,
    onRemovePlayer: (PlayerRating) -> Unit,
    onAddPlayer: (PlayerRating) -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SBC Rating Calculator",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )

            LazyHorizontalGrid(
                modifier = Modifier
                    .weight(1f),
                rows = GridCells.Fixed(10),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                userScrollEnabled = false
            ) {
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
            totalPlayers?.let {
                Text(
                    text = "Número de jogadores adicionados: $totalPlayers",
                    modifier = Modifier.padding(top = 16.dp, bottom = 40.dp)
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
        Text(text = "${playerRating.players} x ${playerRating.rating}")
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
    SBCRatingCalculatorList(playerRatings, 11, {}, {})
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