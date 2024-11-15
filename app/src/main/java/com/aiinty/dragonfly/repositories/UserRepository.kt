package com.aiinty.dragonfly.repositories

import com.aiinty.dragonfly.core.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): User?

    suspend fun saveUser(user: User)

    fun readStringValue(keyValue: String): Flow<String?>

    suspend fun writeStringValue(keyValue: String, value: String)

    fun readBooleanValue(keyValue: String): Flow<Boolean?>

    suspend fun writeBooleanValue(keyValue: String, value: Boolean)
}