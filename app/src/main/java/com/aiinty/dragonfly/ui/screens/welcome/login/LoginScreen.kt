package com.aiinty.dragonfly.ui.screens.welcome.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.repositories.FakeUserRepository
import com.aiinty.dragonfly.ui.TopAppBarState
import com.aiinty.dragonfly.ui.TopAppBarStateProvider
import com.aiinty.dragonfly.ui.components.BaseButton
import com.aiinty.dragonfly.ui.components.BaseCheckbox
import com.aiinty.dragonfly.ui.components.BaseTextField
import com.aiinty.dragonfly.ui.components.DefaultHeader
import com.aiinty.dragonfly.ui.theme.Outline
import com.aiinty.dragonfly.ui.theme.Primary
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import com.aiinty.dragonfly.ui.theme.Secondary
import com.aiinty.dragonfly.ui.theme.SecondaryContainer
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onSuccessfulLogin: () -> Unit,
    onRegisterNavigation: () -> Unit,
    onForgotPassword: () -> Unit
) {
    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isRememberMe = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        TopAppBarStateProvider.update(TopAppBarState {
            DefaultHeader()
        })
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp, 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.login_title),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.login_desc),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BaseTextField(value = login.value, onValueChange = { login.value = it }, label = {
                    Text(
                        text = stringResource(id = R.string.login_email_hint),
                        style = MaterialTheme.typography.labelSmall
                    )
                })
                BaseTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.login_password_hint),
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BaseCheckbox(
                            checked = isRememberMe.value,
                            onCheckedChange = { isRememberMe.value = it },
                        )
                        Text(
                            text = stringResource(id = R.string.login_remember_me),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                    TextButton(
                        onClick = onForgotPassword, contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.login_forgot),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BaseButton(
                    onClick = {
                        runBlocking {
                            val user = loginViewModel.getUser() ?: return@runBlocking

                            if ((login.value == user.email || login.value == user.username) && password.value == user.password) {
                                if (isRememberMe.value) {
                                    user.rememberMe = true
                                    loginViewModel.saveUser(user)
                                }
                                onSuccessfulLogin()
                            }
                        }
                    },
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = MaterialTheme.typography.labelLarge,
                        color = Primary
                    )
                }

                Text(
                    text = stringResource(R.string.login_other),
                    modifier = Modifier.padding(vertical = 10.dp),
                    style = MaterialTheme.typography.labelSmall,
                )

                BaseButton(
                    onClick = { },
                    border = BorderStroke(1.dp, Outline),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryContainer)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(16.dp),
                            bitmap = ImageBitmap.imageResource(R.drawable.google),
                            contentDescription = stringResource(R.string.Google)
                        )
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = stringResource(R.string.login_with_gmail),
                            style = MaterialTheme.typography.labelLarge,
                            color = Secondary
                        )
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login_register_hint),
                style = MaterialTheme.typography.labelSmall
            )
            TextButton(contentPadding = PaddingValues(0.dp), onClick = { onRegisterNavigation() }) {
                Text(
                    text = stringResource(id = R.string.register),
                    style = MaterialTheme.typography.labelSmall,
                    color = PrimaryContainer
                )
            }
        }
    }
}

fun NavController.navigateToLogin(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = LoginRoute, navOptions)

fun NavGraphBuilder.loginScreen(
    onSuccessfulLogin: () -> Unit, onRegisterNavigation: () -> Unit, onForgotPassword: () -> Unit
) {
    composable<LoginRoute> {
        LoginScreen(
            onSuccessfulLogin = onSuccessfulLogin,
            onRegisterNavigation = onRegisterNavigation,
            onForgotPassword = onForgotPassword
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginHeaderPreview() {
    DefaultHeader()
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(loginViewModel = LoginViewModel(FakeUserRepository()),
        onSuccessfulLogin = {},
        onRegisterNavigation = {},
        onForgotPassword = {})
}
