package com.aiinty.dragonfly.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.aiinty.dragonfly.core.entity.USER_EMAIL_KEY
import com.aiinty.dragonfly.core.entity.USER_PASSCODE_KEY
import com.aiinty.dragonfly.core.entity.USER_PASSWORD_KEY
import com.aiinty.dragonfly.core.entity.USER_REMEMBER_ME_KEY
import com.aiinty.dragonfly.core.entity.USER_USERNAME_KEY
import com.aiinty.dragonfly.core.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserRepository {

    override suspend fun getUser(): User? {
        val email = readStringValue(USER_EMAIL_KEY).first()
        val username = readStringValue(USER_USERNAME_KEY).first()
        val password = readStringValue(USER_PASSWORD_KEY).first()
        val passcode = readStringValue(USER_PASSCODE_KEY).first()
        val rememberMe = readBooleanValue(USER_REMEMBER_ME_KEY).first() ?: false

        return if (email != null && username != null && password != null) {
            User(email, username, password, passcode, rememberMe)
        } else return null
    }

    override suspend fun saveUser(user: User) {
        runBlocking {
            writeStringValue(USER_EMAIL_KEY, user.email ?: "")
            writeStringValue(USER_USERNAME_KEY, user.username ?: "")
            writeStringValue(USER_PASSWORD_KEY, user.password ?: "")
            writeStringValue(USER_PASSCODE_KEY, user.passCode ?: "")
            writeBooleanValue(USER_REMEMBER_ME_KEY, user.rememberMe)
        }
    }

    override fun readStringValue(keyValue: String): Flow<String?> {
        val key = stringPreferencesKey(keyValue)
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    override suspend fun writeStringValue(keyValue: String, value: String) {
        val key = stringPreferencesKey(keyValue)
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun readBooleanValue(keyValue: String): Flow<Boolean?> {
        val key = booleanPreferencesKey(keyValue)
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    override suspend fun writeBooleanValue(keyValue: String, value: Boolean) {
        val key = booleanPreferencesKey(keyValue)
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}