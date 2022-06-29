package com.guilhermealbm.sbcratingcalculator.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guilhermealbm.sbcratingcalculator.model.PlayerRating
import com.guilhermealbm.sbcratingcalculator.model.playerRating
import com.guilhermealbm.sbcratingcalculator.repository.PlayerRatingRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class SBCRatingCalculatorViewModelTest {

    private val playerRatingRepository = mockk<PlayerRatingRepository>()
    private lateinit var playersList: ArrayList<PlayerRating>
    private lateinit var viewModel: SBCRatingCalculatorViewModel

    private val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        playersList = Array(10) { i ->
            playerRating {
                rating = 80 + i
                players = 1
            }
        }.toList() as ArrayList<PlayerRating>
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun updatePlayerInListTest() = runTest {
        coEvery { playerRatingRepository.getPlayersRating() } returns flow {
            emit(playersList)
        }
        viewModel = SBCRatingCalculatorViewModel(
            playerRatingRepository
        )

        var playerRating = PlayerRating(83, 1)
        viewModel.updatePlayerInList(playerRating, true)

        assertEquals(
            11,
            viewModel.totalPlayers.value,
        )
        assertEquals(
            85,
            viewModel.teamRating.value
        )

        playerRating = PlayerRating(83, 2)
        viewModel.updatePlayerInList(playerRating, false)
        assertEquals(
            10,
            viewModel.totalPlayers.value,
        )
        assertEquals(
            null,
            viewModel.teamRating.value
        )
    }

    @Test
    fun clearDataTest() = runTest {
        coEvery { playerRatingRepository.getPlayersRating() } returns flow {
            emit(playersList)
        }
        viewModel = SBCRatingCalculatorViewModel(
            playerRatingRepository
        )

        viewModel.clearData()

        assertEquals(
            0,
            viewModel.totalPlayers.value,
        )
        assertEquals(
            null,
            viewModel.teamRating.value
        )
    }

}