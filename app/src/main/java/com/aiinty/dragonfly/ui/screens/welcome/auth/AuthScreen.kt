package com.aiinty.dragonfly.ui.screens.welcome.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.repositories.FakeUserRepository
import com.aiinty.dragonfly.ui.TopAppBarState
import com.aiinty.dragonfly.ui.TopAppBarStateProvider
import com.aiinty.dragonfly.ui.theme.Outline
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import com.aiinty.dragonfly.ui.theme.Red
import com.aiinty.dragonfly.ui.theme.Secondary
import kotlinx.serialization.Serializable

@Serializable
object AuthRoute

enum class PassCodeState {
    NotFull,
    FullNotCorrect,
    FullCorrect
}

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onNextNavigate: () -> Unit
) {
    var passCode by remember { mutableStateOf("") }
    var correctState by remember { mutableStateOf(PassCodeState.NotFull) }

    LaunchedEffect(Unit) {
        TopAppBarStateProvider.update(
            TopAppBarState { }
        )
    }

    Row(
        Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 16.dp),
            verticalArrangement = Arrangement.spacedBy(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(26.dp)
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.lock_icon),
                    contentDescription = stringResource(R.string.lock_icon_desc),
                    tint = Color(0xFF05BE71)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.auth_enter_code),
                        style = MaterialTheme.typography.labelMedium
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(4) { index ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(
                                        1.dp,
                                        if (index >= passCode.length) {
                                            Outline
                                        } else if (correctState == PassCodeState.FullNotCorrect) {
                                            Red
                                        } else
                                        {
                                            PrimaryContainer
                                        },
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .size(48.dp), contentAlignment = Alignment.Center
                            ) {
                                if (index < passCode.length) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ellipse),
                                        "code cell",
                                        Modifier.size(8.dp, 8.dp),
                                        tint = if (index >= passCode.length) {
                                            Color.Unspecified
                                        } else if (correctState == PassCodeState.FullNotCorrect) {
                                            Red
                                        } else
                                        {
                                            PrimaryContainer
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val keys = listOf(
                    listOf("1", "4", "7", ""),
                    listOf("2", "5", "8", "0"),
                    listOf("3", "6", "9", "<")
                )

                Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {

                    keys.forEach { column ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)

                        ) {
                            column.forEach { key ->
                                if (key.isNotEmpty()) {
                                    KeyboardButton(key, onClick = {
                                        val user = authViewModel.user ?: return@KeyboardButton

                                        when (key) {
                                            "<" -> {
                                                if (passCode.isNotEmpty()) {
                                                    passCode = passCode.dropLast(1)
                                                }
                                            }

                                            else -> {
                                                if (passCode.length < 4) {
                                                    passCode += key
                                                    correctState = PassCodeState.NotFull
                                                }

                                                if (passCode.length == 4) {
                                                    if (user.passCode.isNullOrEmpty()) {
                                                        user.passCode = passCode
                                                        authViewModel.saveUser(user)
                                                        onNextNavigate()
                                                    } else if (passCode == user.passCode) {
                                                        correctState = PassCodeState.FullCorrect
                                                        onNextNavigate()
                                                    } else {
                                                        correctState = PassCodeState.FullNotCorrect
                                                    }
                                                }
                                            }
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardButton(key: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick, modifier = Modifier
            .size(64.dp)
            .background(Color.Transparent)
    ) {
        if (key == "<") {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.backspace),
                "Backspace button",
                Modifier
                    .size(32.dp, 32.dp)
                    .padding(end = 8.dp)
            )
        } else {
            Text(
                key, style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 24.sp, color = Secondary, lineHeight = 32.sp
                )
            )
        }
    }
}

fun NavController.navigateToAuth(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = AuthRoute, navOptions)

fun NavGraphBuilder.authScreen(
    onHomeNavigate: () -> Unit
) {
    composable<AuthRoute> {
        AuthScreen(
            onNextNavigate = onHomeNavigate
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    AuthScreen(
        authViewModel = AuthViewModel(FakeUserRepository())
    ) {}
}