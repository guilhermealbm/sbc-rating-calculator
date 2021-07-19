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
            ),
            this
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playerRating = getItem(position)
        (holder as PlayerRatingViewHolder).bind(playerRating)
    }


    class PlayerRatingViewHolder(
        private val binding: PlayerRatingSelectorBinding,
        private val adapter: PlayerRatingAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListenerPlus {
                if (binding.playerRating!!.players + 1 > 11)
                    return@setClickListenerPlus

                val newPlayerRating = PlayerRating(binding.playerRating!!.rating, binding.playerRating!!.players + 1)
                val currentList = adapter.currentList.toMutableList()
                val index = currentList.indexOf(binding.playerRating)
                if (index != -1) {
                    currentList.remove(binding.playerRating)
                    currentList.add(index, newPlayerRating)
                    adapter.submitList(currentList)
                }
            }
            binding.setClickListenerMinus {
                if (binding.playerRating!!.players -1 < 0)
                    return@setClickListenerMinus

                val newPlayerRating = PlayerRating(binding.playerRating!!.rating, binding.playerRating!!.players - 1)
                val currentList = adapter.currentList.toMutableList()
                val index = currentList.indexOf(binding.playerRating)
                if (index != -1) {
                    currentList.remove(binding.playerRating)
                    currentList.add(index, newPlayerRating)
                    adapter.submitList(currentList)
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