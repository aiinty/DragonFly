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
object RegisterEmailRoute

@Composable
private fun RegisterEmailScreen(
    registerViewModel: RegisterViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    onInformationClick: () -> Unit
) {
    val email = remember { mutableStateOf(registerViewModel.user.email) }
    val isCorrect = remember { mutableStateOf(false) }

    LaunchedEffect(registerViewModel.currentRegisterScreenState) {
        TopAppBarStateProvider.update(
            TopAppBarState {
                RegisterHeader(
                    state = RegisterScreenState.EMAIL,
                    previousState = registerViewModel.currentRegisterScreenState,
                    onBackClick = onBackClick,
                    onInformationClick = onInformationClick
                )

                LaunchedEffect(Unit) {
                    registerViewModel.currentRegisterScreenState = RegisterScreenState.EMAIL
                }
            }
        )
    }

    LaunchedEffect(email.value) {
        isCorrect.value = android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    RegisterItemDetails(
        titleId = R.string.register_email,
        descId = R.string.register_email_desc
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BaseTextField(
                value = email.value,
                onValueChange = {
                    isCorrect.value = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    email.value = it
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.register_email),
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
                        text = "Should contains a valid email",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFE324E)
                    )
                }

                BaseButton(
                    onClick =
                    {
                        registerViewModel.user.email = email.value
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

fun NavController.navigateToRegisterEmail(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = RegisterEmailRoute, navOptions)

fun NavGraphBuilder.registerEmailScreen(
    registerViewModel: RegisterViewModel,
    onNextNavigate: () -> Unit,
    onPreviousNavigate: () -> Unit,
    onInformationNavigate: () -> Unit
) {
    composable<RegisterEmailRoute> {
        RegisterEmailScreen(
            registerViewModel = registerViewModel,
            onNextClick = onNextNavigate,
            onBackClick = onPreviousNavigate,
            onInformationClick = onInformationNavigate
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterEmailPreview() {
    RegisterEmailScreen (
        registerViewModel = RegisterViewModel(FakeUserRepository()),
        onNextClick = { },
        onBackClick = { }
    ) { }
}
