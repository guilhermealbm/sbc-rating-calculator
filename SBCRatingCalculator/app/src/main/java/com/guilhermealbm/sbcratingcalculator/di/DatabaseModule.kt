package com.guilhermealbm.sbcratingcalculator.di

import android.content.Context
import com.guilhermealbm.sbcratingcalculator.data.AppDatabase
import com.guilhermealbm.sbcratingcalculator.data.PlayerRatingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePlantDao(appDatabase: AppDatabase): PlayerRatingDao {
        return appDatabase.playerRatingDao()
    }

}