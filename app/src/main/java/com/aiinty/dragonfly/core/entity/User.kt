package com.aiinty.dragonfly.core.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val USER_KEY = "user_object_key"
const val USER_EMAIL_KEY = "user_email_key"
const val USER_USERNAME_KEY = "user_username_key"
const val USER_PASSWORD_KEY = "user_password_key"
const val USER_PASSCODE_KEY = "user_passcode_key"
const val USER_REMEMBER_ME_KEY = "user_remember_me_key"

@Parcelize
data class User(
    var email: String = "",
    var username: String = "",
    var password: String = "",
    var passCode: String? = null,
    var rememberMe: Boolean = false
) : Parcelable
