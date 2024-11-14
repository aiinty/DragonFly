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
object RegisterEmailRoute

@Composable
private fun RegisterEmailScreen(
    user: User,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    onInformationClick: () -> Unit
) {
    val email = remember { mutableStateOf(user.email ?: "") }
    val isCorrect = remember { mutableStateOf(false) }

    LaunchedEffect(email.value) {
        isCorrect.value = android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    Column {
        RegisterHeader(
            state = RegisterScreenState.EMAIL,
            onBackClick = onBackClick,
            onInformationClick = onInformationClick
        )

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

                BaseButton(
                    onClick = {  user.email = email.value; onNextClick(); }, enabled = isCorrect.value
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
    user: User,
    onNextNavigate: () -> Unit,
    onPreviousNavigate: () -> Unit,
    onInformationNavigate: () -> Unit
) {
    composable<RegisterEmailRoute> {
        RegisterEmailScreen(
            user = user,
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
        User(),
        { },
        { }
    ) { }
}
