package com.guilhermealbm.sbcratingcalculator.ui

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guilhermealbm.sbcratingcalculator.R
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import com.guilhermealbm.sbcratingcalculator.model.createRatings
import com.guilhermealbm.sbcratingcalculator.theme.SBCRatingCalculatorTheme
import com.guilhermealbm.sbcratingcalculator.viewmodels.SBCRatingCalculatorViewModel

@Composable
fun SBCRatingCalculator(viewModel: SBCRatingCalculatorViewModel = viewModel()) {
    val playerRatingsList: List<PlayerRating>? by viewModel.playersByRating.observeAsState()
    val totalPlayers: Int? by viewModel.totalPlayers.observeAsState()
    val teamRating: Int? by viewModel.teamRating.observeAsState()
    SBCRatingCalculatorScreen(
        playerRatingsList,
        totalPlayers,
        teamRating,
        onRemovePlayer = { viewModel.updatePlayerInList(it, false) },
        onAddPlayer = { viewModel.updatePlayerInList(it, true) },
        onClearData = { viewModel.clearData() }
    )
}

@Composable
fun SBCRatingCalculatorScreen(
    playerRatingsList: List<PlayerRating>?,
    totalPlayers: Int?,
    teamRating: Int?,
    onRemovePlayer: (PlayerRating) -> Unit,
    onAddPlayer: (PlayerRating) -> Unit,
    onClearData: () -> Unit
) {
    Scaffold {
        Column (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val configuration = LocalConfiguration.current
            val cells = when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 5
                else -> 10
            }
            val marginBottom = when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 20.dp
                else -> 40.dp
            }
            SBCRatingCalculatorHeader()
            Row(
                modifier = Modifier.weight(1f)
            ) {
                SBCRatingCalculatorList(
                    playerRatingsList = playerRatingsList,
                    onRemovePlayer = onRemovePlayer,
                    onAddPlayer = onAddPlayer,
                    cells = cells
                )
            }
            SBCRatingCalculatorInfo(
                totalPlayers = totalPlayers,
                teamRating = teamRating,
                onClearData = onClearData,
                marginBottom = marginBottom
            )
        }
    }
}

@Composable
fun SBCRatingCalculatorHeader() {
    Text(
        text = stringResource(id = R.string.heading),
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SBCRatingCalculatorList(
    playerRatingsList: List<PlayerRating>?,
    onRemovePlayer: (PlayerRating) -> Unit,
    onAddPlayer: (PlayerRating) -> Unit,
    cells: Int,
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(cells),
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

@Composable
fun SBCRatingCalculatorInfo(
    totalPlayers: Int?,
    teamRating: Int?,
    onClearData: () -> Unit,
    marginBottom: Dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        teamRating?.let {
            Text(
                modifier = Modifier.height(20.dp),
                text = stringResource(id = R.string.result_message, teamRating)
            )
        } ?: run {
            Spacer(modifier = Modifier.height(20.dp))
        }
        totalPlayers?.let {
            Text(
                text = stringResource(id = R.string.added_players, totalPlayers),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }
        Button(
            onClick = onClearData,
            modifier = Modifier.padding(bottom = marginBottom)
        ) {
            Text(text = stringResource(id = R.string.clear))
        }
    }
}

@Preview
@Preview(
    name = "SBCRatingCalculator",
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun SBCRatingCalculatorPreview() {
    SBCRatingCalculatorTheme {
        SBCRatingCalculatorScreen(createRatings().toList(), 11, 82, {}, {}, {})
    }
}

@Preview(name = "SBCRatingCalculatorList")
@Composable
private fun SBCRatingCalculatorListPreview() {
    SBCRatingCalculatorTheme {
        SBCRatingCalculatorList(createRatings().toList(), {}, {}, 5)
    }
}

@Preview(name = "SBCRatingCalculatorInfo")
@Composable
private fun SBCRatingCalculatorInfoPreview() {
    SBCRatingCalculatorTheme {
        SBCRatingCalculatorInfo(11, 82, {}, 40.dp)
    }
}

@Preview(name = "PlayerRatingSelector")
@Composable
private fun PlayerRatingSelectorPreview() {
    val playerRating = PlayerRating(80, 1)
    SBCRatingCalculatorTheme {
        PlayerRatingSelector(playerRating, {}, {})
    }
}