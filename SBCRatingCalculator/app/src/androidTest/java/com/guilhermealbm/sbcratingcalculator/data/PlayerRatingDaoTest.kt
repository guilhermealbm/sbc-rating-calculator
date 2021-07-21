package com.guilhermealbm.sbcratingcalculator.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.guilhermealbm.sbcratingcalculator.model.playerRating
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayerRatingDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var playerRatingDao: PlayerRatingDao

    private val playersList = Array(20) { i ->
        playerRating {
            rating = 80 + i
            players = 0
        }
    }.toList()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        playerRatingDao = database.playerRatingDao()

        playerRatingDao.saveAll(playersList)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetPlayersRating() = runBlocking {
        val playerRating80 = playerRating {
            rating = 80
            players = 0
        }
        Assert.assertThat(playerRatingDao.load().first()[0], Matchers.equalTo(playerRating80))
        Assert.assertThat(playerRatingDao.load().first().size, Matchers.equalTo(20))
    }
}