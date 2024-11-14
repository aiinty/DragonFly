package com.aiinty.dragonfly.ui.screens.welcome.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.ui.components.BaseButton
import com.aiinty.dragonfly.ui.components.DefaultHeader
import com.aiinty.dragonfly.ui.theme.Outline
import com.aiinty.dragonfly.ui.theme.Primary
import com.aiinty.dragonfly.ui.theme.Secondary
import com.aiinty.dragonfly.ui.theme.SecondaryContainer
import kotlinx.serialization.Serializable

@Serializable
object RegisterRoute

enum class RegisterScreenState {
    START, EMAIL, USERNAME, PASSWORD;

    fun calculatePercent(): Float = this.ordinal / RegisterScreenState.entries.size.toFloat()
}

//TODO: save RegisterScreenState to RegisterViewModel
@Composable
fun RegisterScreen(
    user: User,
    onNextNavigate: (User) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        DefaultHeader()

        RegisterItemDetails(
            titleId = R.string.register,
            descId = R.string.register_desc
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
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
                                text = stringResource(R.string.register_with_gmail),
                                style = MaterialTheme.typography.labelSmall,
                                color = Secondary
                            )
                        }
                    }
                    Text(
                        text = stringResource(R.string.register_other),
                        style = MaterialTheme.typography.labelSmall,
                    )
                    BaseButton (
                        onClick = { onNextNavigate(user) },
                    ) {
                        Text(
                            text = stringResource(R.string.register_with_email),
                            style = MaterialTheme.typography.labelSmall,
                            color = Primary
                        )
                    }
                }
            }
        }
    }
}

fun NavController.navigateToRegister(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = RegisterRoute, navOptions)

fun NavGraphBuilder.registerScreen(
    user: User,
    onEmailNavigate: (User) -> Unit,
    registerDestination: NavGraphBuilder.() -> Unit
) {
    composable<RegisterRoute> {
        RegisterScreen(user, onNextNavigate = onEmailNavigate)
    }
    registerDestination()
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    RegisterScreen(
        User(),
        onNextNavigate = { }
    )
}
