package com.guilhermealbm.sbcratingcalculator.util

import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import kotlin.math.roundToInt

fun calculateSquadRating(players: List<PlayerRating>) : Int {
    val squadSum = getSquadSum(players)
    val avg = squadSum/11.0
    val cf = getCF(players, avg)
    return ((squadSum + cf).roundToInt() / 11.0).toInt()
}

fun getSquadSum(players: List<PlayerRating>) = players.map { p -> p.players * p.rating }.sum()

fun getCF(players: List<PlayerRating>, avg: Double) = players.filter { p -> p.rating > avg }.map { p -> p.players * p.rating - p.players * avg}.sum()