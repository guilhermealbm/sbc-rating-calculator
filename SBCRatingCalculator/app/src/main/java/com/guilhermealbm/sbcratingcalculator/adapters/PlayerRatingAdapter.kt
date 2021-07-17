package com.guilhermealbm.sbcratingcalculator.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.guilhermealbm.sbcratingcalculator.databinding.PlayerRatingSelectorBinding
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating

class PlayerRatingAdapter : ListAdapter<PlayerRating, RecyclerView.ViewHolder>(PlayerRatingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlayerRatingViewHolder(
            PlayerRatingSelectorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playerRating = getItem(position)
        (holder as PlayerRatingViewHolder).bind(playerRating)
    }


    class PlayerRatingViewHolder(
        private val binding: PlayerRatingSelectorBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.playerRating?.let { playerRating ->
                    println("testando..." + playerRating.players + "  " + playerRating.rating)
                }
            }
        }

        fun bind(item: PlayerRating) {
            binding.apply {
                playerRating = item
                executePendingBindings()
            }
        }

    }

}

private class PlayerRatingDiffCallback : DiffUtil.ItemCallback<PlayerRating>() {

    override fun areItemsTheSame(oldItem: PlayerRating, newItem: PlayerRating): Boolean {
        return oldItem.rating == newItem.rating
    }

    override fun areContentsTheSame(oldItem: PlayerRating, newItem: PlayerRating): Boolean {
        return oldItem == newItem
    }
}