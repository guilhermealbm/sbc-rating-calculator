package com.guilhermealbm.sbcratingcalculator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.guilhermealbm.sbcratingcalculator.viewmodels.SBCRatingCalculatorViewModel
import com.guilhermealbm.sbcratingcalculator.R
import com.guilhermealbm.sbcratingcalculator.adapters.PlayerRatingAdapter
import com.guilhermealbm.sbcratingcalculator.model.createRatings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.sbc_rating_calculator_fragment.*

@AndroidEntryPoint
class SBCRatingCalculatorFragment : Fragment() {

    private val viewModel: SBCRatingCalculatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.sbc_rating_calculator_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player_rating_selector_list_view.layoutManager = LinearLayoutManager(context)
        viewModel.playersByRating.observe(viewLifecycleOwner) {
            if (it.size != 30) {
                val newList = createRatings()
                newList.forEach { threePositionList ->
                    run {
                        threePositionList.forEach { playerRating ->
                            run {
                                viewModel.savePlayerRatingDb(playerRating)
                            }
                        }
                    }
                }

            } else {
                val playersArray = Array(10) { i ->
                    arrayOf(
                        it[i],
                        it[i + 10],
                        it[i + 20]
                    )
                }
                player_rating_selector_list_view.adapter = PlayerRatingAdapter(playersArray)
            }
        }
    }

}