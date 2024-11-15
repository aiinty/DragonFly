package com.aiinty.dragonfly.ui.screens.welcome.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
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
    viewModel: RegisterViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    onInformationClick: () -> Unit
) {
    val username = remember { mutableStateOf("") }
    val isCorrect = remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.currentRegisterScreenState) {
        TopAppBarStateProvider.update(
            TopAppBarState {
                RegisterHeader(
                    state = RegisterScreenState.USERNAME,
                    previousState = viewModel.currentRegisterScreenState,
                    onBackClick = onBackClick,
                    onInformationClick = onInformationClick
                )

                LaunchedEffect(Unit) {
                    viewModel.currentRegisterScreenState = RegisterScreenState.USERNAME
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
            BaseButton(
                onClick =
                {
                    viewModel.user.username = username.value
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
            viewModel = registerViewModel,
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
        viewModel = hiltViewModel(),
        onNextClick = { },
        onBackClick = { }
    ) { }
}
