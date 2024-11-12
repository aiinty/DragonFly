package com.aiinty.dragonfly.ui.screens

import androidx.compose.runtime.Composable
import com.aiinty.dragonfly.core.entity.User
import kotlinx.serialization.Serializable

@Serializable
data class Auth(val user: User)

@Composable
fun AuthScreen(
    user: User
) {

}