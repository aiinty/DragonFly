package com.aiinty.dragonfly.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aiinty.dragonfly.core.entity.User
import kotlinx.serialization.Serializable

@Serializable
object Profile

@Composable
fun ProfileScreen(
    user: User
) {

}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(User("Example", "Example", "1234", true))
}