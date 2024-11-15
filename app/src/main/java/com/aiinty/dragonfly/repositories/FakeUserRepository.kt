package com.aiinty.dragonfly.repositories

import com.aiinty.dragonfly.core.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository : UserRepository {
    override suspend fun getUser(): User? {
        return User(
            "Example email",
            "Example username",
            "example",
            "1111"
        )
    }

    override suspend fun saveUser(user: User) { }

    override fun readStringValue(keyValue: String): Flow<String?> {
        return flow {  }
    }

    override suspend fun writeStringValue(keyValue: String, value: String) { }

    override fun readBooleanValue(keyValue: String): Flow<Boolean?> {
        return flow {  }
    }

    override suspend fun writeBooleanValue(keyValue: String, value: Boolean) { }
}