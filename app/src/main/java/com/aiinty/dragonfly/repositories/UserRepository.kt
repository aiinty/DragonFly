package com.aiinty.dragonfly.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.core.entity.USER_EMAIL_KEY
import com.aiinty.dragonfly.core.entity.USER_IS_REGISTERED_KEY
import com.aiinty.dragonfly.core.entity.USER_PASSCODE_KEY
import com.aiinty.dragonfly.core.entity.USER_PASSWORD_KEY
import com.aiinty.dragonfly.core.entity.USER_REMEMBER_ME_KEY
import com.aiinty.dragonfly.core.entity.USER_USERNAME_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun getUser(): User {
        val isRegistered = readBooleanValue(USER_IS_REGISTERED_KEY).first() ?: false
        return if (isRegistered) {
            val email = readStringValue(USER_EMAIL_KEY).first() ?: ""
            val username = readStringValue(USER_USERNAME_KEY).first() ?: ""
            val password = readStringValue(USER_PASSWORD_KEY).first() ?: ""
            val passcode = readStringValue(USER_PASSCODE_KEY).first() ?: ""
            val rememberMe = readBooleanValue(USER_REMEMBER_ME_KEY).first() ?: false
            User(email, username, password, passcode, rememberMe, isRegistered)
        } else {
            User()
        }
    }

    suspend fun saveUser(user: User) {
        writeStringValue(USER_EMAIL_KEY, user.email ?: "")
        writeStringValue(USER_USERNAME_KEY, user.username ?: "")
        writeStringValue(USER_PASSWORD_KEY, user.password ?: "")
        writeStringValue(USER_PASSCODE_KEY, user.passCode ?: "")
        writeBooleanValue(USER_REMEMBER_ME_KEY, user.rememberMe)
        writeBooleanValue(USER_IS_REGISTERED_KEY, user.isRegistered)
    }

    private fun readStringValue(keyValue: String): Flow<String?> {
        val key = stringPreferencesKey(keyValue)
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    private suspend fun writeStringValue(keyValue: String, value: String) {
        val key = stringPreferencesKey(keyValue)
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private fun readBooleanValue(keyValue: String): Flow<Boolean?> {
        val key = booleanPreferencesKey(keyValue)
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    private suspend fun writeBooleanValue(keyValue: String, value: Boolean) {
        val key = booleanPreferencesKey(keyValue)
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}