package com.sevenpeakssoftware.kyawlinnthant.ds

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.sevenpeakssoftware.kyawlinnthant.data.ds.PrefDataStore
import com.sevenpeakssoftware.kyawlinnthant.data.ds.PrefModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import kotlin.random.Random

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [PrefModule::class]
)
object TestDsModule {

    @Provides
    @Singleton
    fun provideTestPref(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile("${PrefDataStore.PREF_NAME}${Random.nextInt()}")
        }
    )
}