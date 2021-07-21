package com.guilhermealbm.sbcratingcalculator.util

import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import com.guilhermealbm.sbcratingcalculator.model.playerRating
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class SBCRatingOperationsTest {

    private lateinit var playersList: ArrayList<PlayerRating>

    @Before
    fun setUp() {
        playersList = Array(20) { i ->
            playerRating {
                rating = 80 + i
                players = 0
            }
        }.toList() as ArrayList<PlayerRating>
    }

    //https://www.futbin.com/sbc-rating-combinations?rating=83
    @Test
    fun testCalculateSquadRating83() {
        val players83 = playerRating {
            rating = 83
            players = 9
        }
        val players82 = playerRating {
            rating = 82
            players = 2
        }

        playersList[3] = players83
        playersList[2] = players82

        assertEquals(83, calculateSquadRating(playersList))
    }

    @Test
    fun testCalculateSquadRating82() {
        val players83 = playerRating {
            rating = 83
            players = 8
        }
        val players82 = playerRating {
            rating = 82
            players = 3
        }

        playersList[3] = players83
        playersList[2] = players82

        assertEquals(82, calculateSquadRating(playersList))
    }

}