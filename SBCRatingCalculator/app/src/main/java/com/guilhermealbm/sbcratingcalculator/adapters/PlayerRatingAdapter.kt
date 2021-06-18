package com.guilhermealbm.sbcratingcalculator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guilhermealbm.sbcratingcalculator.R
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating

class PlayerRatingAdapter (private val ratings : Array<Array<PlayerRating>>) :
    RecyclerView.Adapter<PlayerRatingAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val playerSelector1: View = view.findViewById(R.id._7x_selector)
        val playerRating1: TextView = playerSelector1.findViewById(R.id.player_rating)
        val addPlayerBtn1: Button = playerSelector1.findViewById(R.id.add_btn)
        val subPlayerBtn1: Button = playerSelector1.findViewById(R.id.sub_btn)

        private val playerSelector2: View = view.findViewById(R.id._8x_selector)
        val playerRating2: TextView = playerSelector2.findViewById(R.id.player_rating)
        val addPlayerBtn2: Button = playerSelector2.findViewById(R.id.add_btn)
        val subPlayerBtn2: Button = playerSelector2.findViewById(R.id.sub_btn)

        private val playerSelector3: View = view.findViewById(R.id._9x_selector)
        val playerRating3: TextView = playerSelector3.findViewById(R.id.player_rating)
        val addPlayerBtn3: Button = playerSelector3.findViewById(R.id.add_btn)
        val subPlayerBtn3: Button = playerSelector3.findViewById(R.id.sub_btn)

        init {
            addSubPlayersOnClickListener(addPlayerBtn1, subPlayerBtn1, 0)
            addSubPlayersOnClickListener(addPlayerBtn2, subPlayerBtn2, 1)
            addSubPlayersOnClickListener(addPlayerBtn3, subPlayerBtn3, 2)
        }


        private fun addSubPlayersOnClickListener(addBtn: Button, subBtn: Button, index: Int) {
            addBtn.setOnClickListener {
                println("+ $bindingAdapterPosition $index")
            }
            subBtn.setOnClickListener {
                println("- $bindingAdapterPosition $index")
            }
        }

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