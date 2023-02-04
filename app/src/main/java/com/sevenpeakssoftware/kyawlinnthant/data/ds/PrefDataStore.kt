package com.sevenpeakssoftware.kyawlinnthant.data.ds

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow

interface PrefDataStore {
    companion object {
        const val PREF_NAME = "cars.ds.pref"
        val ENABLED_DYNAMIC = booleanPreferencesKey("ds.enabled.dynamic")
        val SELECTED_THEME = intPreferencesKey("ds.app.theme")
    }

    suspend fun putDynamicColor(isEnabled: Boolean)
    suspend fun pullDynamicColor(): Flow<Boolean?>
    suspend fun putThemeColor(value: Int)
    suspend fun pullThemeColor(): Flow<Int?>
    suspend fun clear()
}