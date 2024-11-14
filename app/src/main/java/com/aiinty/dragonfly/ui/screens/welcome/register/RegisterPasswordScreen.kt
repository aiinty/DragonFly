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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.ui.components.BaseButton
import com.aiinty.dragonfly.ui.components.BaseTextField
import com.aiinty.dragonfly.ui.theme.Primary
import kotlinx.serialization.Serializable

@Serializable
object RegisterPasswordRoute

// TODO: password len check
@Composable
private fun RegisterPasswordScreen(
    user: User,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val password = remember { mutableStateOf("") }
    val passwordRe = remember { mutableStateOf("") }
    val isCorrect = remember { mutableStateOf(false) }

    LaunchedEffect(password.value, passwordRe.value) {
        isCorrect.value = password.value == passwordRe.value && password.value.isNotEmpty()
    }

    Column {
        RegisterHeader(
            RegisterScreenState.PASSWORD,
            { onBackClick() },
            { }
        )

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
                        onValueChange = { password.value = it; isCorrect.value = (password.value == passwordRe.value) },
                        visualTransformation = PasswordVisualTransformation(),
                        label = {
                            Text(
                                text = stringResource(id = R.string.register_password),
                                style = MaterialTheme.typography.labelSmall
                            ) }
                    )

                    BaseTextField(
                        value = passwordRe.value,
                        onValueChange = { passwordRe.value = it; isCorrect.value = (password.value == passwordRe.value) },
                        visualTransformation = PasswordVisualTransformation(),
                        label = {
                            Text(
                                text = stringResource(id = R.string.register_password_re),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }

                BaseButton(
                    onClick = {
                        user.password = password.value
                        onNextClick()
                    }, enabled = isCorrect.value
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
    user: User,
    onNextNavigate: () -> Unit,
    onPreviousNavigate: () -> Unit
) {
    composable<RegisterPasswordRoute> {
        RegisterPasswordScreen(
            user = user,
            onNextClick = onNextNavigate,
            onBackClick = onPreviousNavigate
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterPasswordPreview() {
    RegisterPasswordScreen (
        User(),
        { }
    ) { }
}
