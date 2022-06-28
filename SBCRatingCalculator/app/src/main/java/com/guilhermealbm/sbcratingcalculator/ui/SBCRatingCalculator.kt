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
import com.guilhermealbm.sbcratingcalculator.model.createRatings
import com.guilhermealbm.sbcratingcalculator.viewmodels.SBCRatingCalculatorViewModel

@Composable
fun SBCRatingCalculator(viewModel: SBCRatingCalculatorViewModel = viewModel()) {
    val playerRatingsList: List<PlayerRating>? by viewModel.playersByRating.observeAsState()
    val totalPlayers: Int? by viewModel.totalPlayers.observeAsState()
    val teamRating: Int? by viewModel.teamRating.observeAsState()
    SBCRatingCalculatorList(
        playerRatingsList,
        totalPlayers,
        teamRating,
        onRemovePlayer = { viewModel.updatePlayerInList(it, false) },
        onAddPlayer = { viewModel.updatePlayerInList(it, true) },
        onClearData = { viewModel.clearData() }
    )
}

@Composable
fun SBCRatingCalculatorList(
    playerRatingsList: List<PlayerRating>?,
    totalPlayers: Int?,
    teamRating: Int?,
    onRemovePlayer: (PlayerRating) -> Unit,
    onAddPlayer: (PlayerRating) -> Unit,
    onClearData: () -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
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
            Spacer(modifier = Modifier.height(24.dp))
            teamRating?.let { 
                Text(
                    modifier = Modifier.height(20.dp),
                    text = "O rating de seu squad é: $teamRating"
                )
            } ?: run {
                Spacer(modifier = Modifier.height(20.dp))
            }
            totalPlayers?.let {
                Text(
                    text = "Número de jogadores adicionados: $totalPlayers",
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                )
            }
            Button(
                onClick = onClearData,
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Text(text = "Limpar dados")
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
    SBCRatingCalculatorList(createRatings().toList(), 11, 82, {}, {}, {})
}

@Preview(name = "PlayerRatingSelector")
@Composable
private fun PlayerRatingSelectorPreview() {
    val playerRating = PlayerRating(80, 1)
    Surface {
        PlayerRatingSelector(playerRating, {}, {})
    }
}