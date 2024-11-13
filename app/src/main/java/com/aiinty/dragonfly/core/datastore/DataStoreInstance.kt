package com.aiinty.dragonfly.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aiinty.dragonfly.core.entity.USER_EMAIL_KEY
import com.aiinty.dragonfly.core.entity.USER_IS_REGISTERED_KEY
import com.aiinty.dragonfly.core.entity.USER_PASSCODE_KEY
import com.aiinty.dragonfly.core.entity.USER_PASSWORD_KEY
import com.aiinty.dragonfly.core.entity.USER_REMEMBER_ME_KEY
import com.aiinty.dragonfly.core.entity.USER_USERNAME_KEY
import com.aiinty.dragonfly.core.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

const val PREFS_NAME = "settings"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

object DataStoreInstance {

    suspend fun readUser(context: Context): User {
        if (readBooleanValue(context, USER_IS_REGISTERED_KEY).first() == true) {
            val email = readStringValue(context, USER_EMAIL_KEY).first()
            val username = readStringValue(context, USER_USERNAME_KEY).first()
            val password = readStringValue(context, USER_PASSWORD_KEY).first()
            val passcode = readStringValue(context, USER_PASSCODE_KEY).first()
            val rememberMe = readBooleanValue(context, USER_REMEMBER_ME_KEY).first() ?: false
            val isRegistered = readBooleanValue(context, USER_IS_REGISTERED_KEY).first() ?: false

            return User(email, username, password, passcode, rememberMe, isRegistered)
        }
        return User()
    }

    suspend fun writeUser(context: Context, value: User) {
        writeStringValue(context, USER_EMAIL_KEY, value.email!!)
        writeStringValue(context, USER_USERNAME_KEY, value.username!!)
        writeStringValue(context, USER_PASSWORD_KEY, value.password!!)
        writeStringValue(context, USER_PASSCODE_KEY, value.passCode!!)
        writeBooleanValue(context, USER_REMEMBER_ME_KEY, value.rememberMe)
        writeBooleanValue(context, USER_IS_REGISTERED_KEY, value.isRegistered)
    }

     fun readStringValue(context: Context, keyValue: String): Flow<String?> {
        val key = stringPreferencesKey(keyValue)
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

    fun readBooleanValue(context: Context, keyValue: String): Flow<Boolean?> {
        val key = booleanPreferencesKey(keyValue)
        return context.dataStore.data
            .map { preferences ->
                preferences[key]
            }
    }

    suspend fun writeBooleanValue(context: Context, keyValue: String, value: Boolean) {
        val key = booleanPreferencesKey(keyValue)
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}
