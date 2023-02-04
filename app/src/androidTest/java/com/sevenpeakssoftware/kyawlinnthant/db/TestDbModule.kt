package com.sevenpeakssoftware.kyawlinnthant.db

import android.content.Context
import androidx.room.Room
import com.sevenpeakssoftware.kyawlinnthant.data.db.CarsDatabase
import com.sevenpeakssoftware.kyawlinnthant.data.db.DbModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DbModule::class]
)
object TestDbModule {
    @Provides
    @Singleton
    fun provideInMemoryDb(
        @ApplicationContext context : Context
    ) : CarsDatabase = Room.inMemoryDatabaseBuilder(
        context,CarsDatabase::class.java
    ).allowMainThreadQueries().build()
}