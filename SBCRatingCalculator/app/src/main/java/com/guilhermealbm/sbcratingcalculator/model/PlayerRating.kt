package com.guilhermealbm.sbcratingcalculator.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayerRating(
    @PrimaryKey val rating: Int,
    val players: Int
)

class PlayerRatingBuilder {
    var rating: Int = 0
    var players: Int = 0

    fun build() : PlayerRating = PlayerRating(rating, players)
}

fun playerRating(block: PlayerRatingBuilder.() -> Unit): PlayerRating = PlayerRatingBuilder().apply(block).build()

fun createRatings() = Array(30) { i ->
    playerRating {
        rating = 70 + i
        players = 0
    }
}