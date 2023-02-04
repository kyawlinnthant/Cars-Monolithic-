package com.sevenpeakssoftware.kyawlinnthant.data.ds

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefModule {
    @Provides
    @Singleton
    fun providePref(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile(PrefDataStore.PREF_NAME)
        }
    )
}

@Module
@InstallIn(SingletonComponent::class)
interface PrefStore {
    @Singleton
    @Binds
    fun bindDataStore(pref: PrefDataStoreImpl): PrefDataStore
}