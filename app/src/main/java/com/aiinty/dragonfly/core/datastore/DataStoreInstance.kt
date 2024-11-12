package com.aiinty.dragonfly.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val PREFS_NAME = "settings"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

object DataStoreInstance {

     fun readStringValue(context: Context, keyValue: String): Flow<String?> {
        val key = stringPreferencesKey(keyValue)
        return context.dataStore.data
            .map { preferences ->
                    preferences[key]
            }
    }

    fun readBooleanValue(context: Context, keyValue: String): Flow<Boolean?> {
        val key = booleanPreferencesKey(keyValue)
        return context.dataStore.data
            .map { preferences ->
                preferences[key]
            }
    }

    suspend fun writeStringValue(context: Context, keyValue: String, value: String) {
        val key = stringPreferencesKey(keyValue)
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun writeBooleanValue(context: Context, keyValue: String, value: Boolean) {
        val key = booleanPreferencesKey(keyValue)
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}
