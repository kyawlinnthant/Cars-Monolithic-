package com.sevenpeakssoftware.kyawlinnthant.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CarsDatabase = Room.databaseBuilder(
        context,
        CarsDatabase::class.java,
        CarsDatabase.CARS_DB
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(
        db: CarsDatabase
    ): CarsDao = db.getCarsDao()
}