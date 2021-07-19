package com.guilhermealbm.sbcratingcalculator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.guilhermealbm.sbcratingcalculator.R
import com.guilhermealbm.sbcratingcalculator.viewmodels.SBCRatingCalculatorViewModel
import com.guilhermealbm.sbcratingcalculator.adapters.PlayerRatingAdapter
import com.guilhermealbm.sbcratingcalculator.databinding.SbcRatingCalculatorFragmentBinding
import com.guilhermealbm.sbcratingcalculator.util.getPlayersNum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SBCRatingCalculatorFragment : Fragment() {

    private val viewModel: SBCRatingCalculatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = SbcRatingCalculatorFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = PlayerRatingAdapter()
        binding.playerRatingSelectorListView.adapter = adapter
        subscribeUi(adapter)

        val resultText = binding.textResult

        binding.calculateButton.setOnClickListener {
            updateData(adapter, resultText)
        }

        return binding.root
    }

    private fun subscribeUi(adapter: PlayerRatingAdapter) {
        viewModel.playersByRating.observe(viewLifecycleOwner) { players ->
            adapter.submitList(players)
        }
    }

    private fun updateData(adapter: PlayerRatingAdapter, resultText: TextView) {
        with(viewModel) {
            val playersList = adapter.currentList
            val playersNum = getPlayersNum(playersList)
            when {
                playersNum < 11 -> resultText.text = resources.getString(R.string.missing_players_message)
                playersNum == 11 -> resultText.text = resources.getString(R.string.result_message, getSquadRating(playersList))
                playersNum > 11 -> resultText.text = resources.getString(R.string.too_many_players_message)
            }
            savePlayersRatingDb(playersList)
        }
    }
}