package com.guilhermealbm.sbcratingcalculator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guilhermealbm.sbcratingcalculator.R
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating

class PlayerRatingAdapter (private val ratings : Array<Array<PlayerRating>>) :
    RecyclerView.Adapter<PlayerRatingAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val playerSelector1: View = view.findViewById(R.id._7x_selector)
        val playerRating1: TextView = playerSelector1.findViewById(R.id.player_rating)

        private val playerSelector2: View = view.findViewById(R.id._8x_selector)
        val playerRating2: TextView = playerSelector2.findViewById(R.id.player_rating)

        private val playerSelector3: View = view.findViewById(R.id._9x_selector)
        val playerRating3: TextView = playerSelector3.findViewById(R.id.player_rating)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.player_rating_selector_row, viewGroup, false)

        return ViewHolder(view)
    }


    override fun getItemCount() = ratings.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.playerRating1.text = "${ratings[position][0].players} x ${ratings[position][0].rating}"
        viewHolder.playerRating2.text = "${ratings[position][1].players} x ${ratings[position][1].rating}"
        viewHolder.playerRating3.text = "${ratings[position][2].players} x ${ratings[position][2].rating}"
    }

}