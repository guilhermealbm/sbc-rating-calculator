package com.guilhermealbm.sbcratingcalculator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.guilhermealbm.sbcratingcalculator.viewmodels.SBCRatingCalculatorViewModel
import com.guilhermealbm.sbcratingcalculator.adapters.PlayerRatingAdapter
import com.guilhermealbm.sbcratingcalculator.databinding.SbcRatingCalculatorFragmentBinding
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

        return binding.root
    }

    private fun subscribeUi(adapter: PlayerRatingAdapter) {
        viewModel.playersByRating.observe(viewLifecycleOwner) { players ->
            adapter.submitList(players)
        }
    }
}