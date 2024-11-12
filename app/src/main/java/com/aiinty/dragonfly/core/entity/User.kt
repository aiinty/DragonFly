package com.aiinty.dragonfly.core.entity

import kotlinx.serialization.Serializable

const val USER_LOGIN_KEY = "user_login"
const val USER_PASSWORD_KEY = "user_password"
const val USER_PASSCODE_KEY = "user_passcode"
const val USER_REMEMBER_ME_KEY = "user_remember_me"


@Serializable
data class User(
    val login: String?,
    val password: String?,
    val passCode: String?,
    val rememberMe: Boolean?
)