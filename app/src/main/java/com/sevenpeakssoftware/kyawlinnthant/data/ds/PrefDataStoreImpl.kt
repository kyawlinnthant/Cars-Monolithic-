package com.sevenpeakssoftware.kyawlinnthant.data.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.sevenpeakssoftware.kyawlinnthant.core.DispatchersModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class PrefDataStoreImpl @Inject constructor(
    private val pref: DataStore<Preferences>,
    @DispatchersModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) : PrefDataStore {
    override suspend fun putDynamicColor(isEnabled: Boolean) {
        withContext(dispatcher) {
            pref.edit {
                it[PrefDataStore.ENABLED_DYNAMIC] = isEnabled
            }
        }
    }

    override suspend fun pullDynamicColor(): Flow<Boolean?> {
        return pref.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map {
            it[PrefDataStore.ENABLED_DYNAMIC]
        }.flowOn(dispatcher)
    }

    override suspend fun putThemeColor(value: Int) {
        withContext(dispatcher) {
            pref.edit {
                it[PrefDataStore.SELECTED_THEME] = value
            }
        }
    }

    override suspend fun pullThemeColor(): Flow<Int?> {
        return pref.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map {
            it[PrefDataStore.SELECTED_THEME]
        }.flowOn(dispatcher)
    }

    override suspend fun clear() {
        withContext(dispatcher) {
            pref.edit {
                it.clear()
            }
        }
    }
}