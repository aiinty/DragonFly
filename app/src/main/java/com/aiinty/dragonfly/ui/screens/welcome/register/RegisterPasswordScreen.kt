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
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
object RegisterPasswordRoute

@Composable
private fun RegisterPasswordScreen(
    registerViewModel: RegisterViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    onInformationClick: () -> Unit
) {
    val password = remember { mutableStateOf("") }
    val passwordRe = remember { mutableStateOf("") }
    val isCorrect = remember { mutableStateOf(false) }

    LaunchedEffect(registerViewModel.currentRegisterScreenState) {
        TopAppBarStateProvider.update(
            TopAppBarState {
                RegisterHeader(
                    state = RegisterScreenState.PASSWORD,
                    previousState = registerViewModel.currentRegisterScreenState,
                    onBackClick = onBackClick,
                    onInformationClick = onInformationClick,
                )

                LaunchedEffect(Unit) {
                    registerViewModel.currentRegisterScreenState = RegisterScreenState.PASSWORD
                }
            }
        )
    }

    LaunchedEffect(password.value, passwordRe.value) {
        isCorrect.value = (
                password.value == passwordRe.value &&
                        password.value.length >= 8 &&
                        password.value.contains(Regex("^[0-9!@#\$%^&*()_+]+$"))
                )
    }

    RegisterItemDetails(
        titleId = R.string.register_password,
        descId =  R.string.register_password_desc
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BaseTextField(
                    value = password.value,
                    onValueChange =
                    {
                        password.value = it
                        isCorrect.value = (
                                password.value == passwordRe.value &&
                                        password.value.length >= 8 &&
                                        password.value.contains(Regex("^[0-9!@#\$%^&*()_+]+$"))
                                )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    label = {
                        Text(
                            text = stringResource(id = R.string.register_password),
                            style = MaterialTheme.typography.labelSmall
                        ) }
                )

                BaseTextField(
                    value = passwordRe.value,
                    onValueChange =
                    {
                        passwordRe.value = it
                        isCorrect.value = (
                                password.value == passwordRe.value &&
                                        password.value.length >= 8 &&
                                        password.value.contains(Regex("^[0-9!@#\$%^&*()_+]+$"))
                                )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    label = {
                        Text(
                            text = stringResource(id = R.string.register_password_re),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = !isCorrect.value,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "Password must be eight length and contains only special characters and numbers",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFE324E)
                    )
                }

                BaseButton(
                    onClick =
                    {
                        registerViewModel.user.password = password.value
                        registerViewModel.saveRegisteredUser()
                        onNextClick()
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

fun NavController.navigateToRegisterPassword(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = RegisterPasswordRoute, navOptions)

fun NavGraphBuilder.registerPasswordScreen(
    registerViewModel: RegisterViewModel,
    onNextNavigate: () -> Unit,
    onPreviousNavigate: () -> Unit,
    onInformationNavigate: () -> Unit
) {
    composable<RegisterPasswordRoute> {
        RegisterPasswordScreen(
            registerViewModel = registerViewModel,
            onNextClick = onNextNavigate,
            onBackClick = onPreviousNavigate,
            onInformationClick = onInformationNavigate
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterPasswordPreview() {
    RegisterPasswordScreen (
        registerViewModel = RegisterViewModel(FakeUserRepository()),
        onNextClick = { },
        onBackClick = { }
    ) { }
}
