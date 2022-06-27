package com.guilhermealbm.sbcratingcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.guilhermealbm.sbcratingcalculator.viewmodels.SBCRatingCalculatorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SBCRatingCalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SBCRatingCalculatorApp()
        }
    }

    override fun onStop() {
        viewModel.playersByRating.value?.let {
            viewModel.savePlayersRatingDb(it)
        }
        super.onStop()
    }
}