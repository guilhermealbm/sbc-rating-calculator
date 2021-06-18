package com.guilhermealbm.sbcratingcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.guilhermealbm.sbcratingcalculator.adapters.PlayerRatingAdapter
import com.guilhermealbm.sbcratingcalculator.model.createRatings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player_rating_selector_list_view.adapter = PlayerRatingAdapter(createRatings())
        player_rating_selector_list_view.layoutManager = LinearLayoutManager(this)
    }
}