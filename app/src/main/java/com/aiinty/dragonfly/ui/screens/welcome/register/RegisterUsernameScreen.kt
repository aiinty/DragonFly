package com.aiinty.dragonfly.ui.screens.welcome.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.repositories.FakeUserRepository
import com.aiinty.dragonfly.ui.TopAppBarState
import com.aiinty.dragonfly.ui.TopAppBarStateProvider
import com.aiinty.dragonfly.ui.components.BaseButton
import com.aiinty.dragonfly.ui.components.BaseTextField
import com.aiinty.dragonfly.ui.theme.Primary
import kotlinx.serialization.Serializable

@Serializable
object RegisterUsernameRoute

@Composable
private fun RegisterUsernameScreen(
    registerViewModel: RegisterViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    onInformationClick: () -> Unit
) {
    val username = remember { mutableStateOf(registerViewModel.user.username) }
    val isCorrect = remember { mutableStateOf(false) }

    LaunchedEffect(registerViewModel.currentRegisterScreenState) {
        TopAppBarStateProvider.update(
            TopAppBarState {
                RegisterHeader(
                    state = RegisterScreenState.USERNAME,
                    previousState = registerViewModel.currentRegisterScreenState,
                    onBackClick = onBackClick,
                    onInformationClick = onInformationClick
                )

                LaunchedEffect(Unit) {
                    registerViewModel.currentRegisterScreenState = RegisterScreenState.USERNAME
                }
            }
        )
    }

    LaunchedEffect(username.value) {
        isCorrect.value = username.value.isNotEmpty()
    }

    RegisterItemDetails(
        titleId = R.string.register_username,
        descId = R.string.register_username_desc
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            BaseTextField(
                value = username.value,
                onValueChange = {
                    if (it.takeLast(1).contains(Regex("[A-Za-z_]"))) {
                        username.value = it
                    }
                    isCorrect.value = username.value.isNotEmpty()
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.register_username),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = !isCorrect.value,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "Username should contain english letters and underscore",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFE324E)
                    )
                }
                BaseButton(
                    onClick =
                    {
                        registerViewModel.user.username = username.value
                        onNextClick();
                    },
                    enabled = isCorrect.value
                ) {
                    Text(
                        text = stringResource(R.string.register_next),
                        style = MaterialTheme.typography.labelSmall,
                        color = Primary
                    )
                }
            }
        }
    }
}

fun NavController.navigateToRegisterUsername(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = RegisterUsernameRoute, navOptions)

fun NavGraphBuilder.registerUsernameScreen(
    registerViewModel: RegisterViewModel,
    onNextNavigate: () -> Unit,
    onPreviousNavigate: () -> Unit,
    onInformationNavigate: () -> Unit
) {
    composable<RegisterUsernameRoute> {
        RegisterUsernameScreen(
            registerViewModel = registerViewModel,
            onNextClick = onNextNavigate,
            onBackClick = onPreviousNavigate,
            onInformationClick = onInformationNavigate
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterUsernamePreview() {
    RegisterUsernameScreen(
        registerViewModel = RegisterViewModel(FakeUserRepository()),
        onNextClick = { },
        onBackClick = { }
    ) { }
}
