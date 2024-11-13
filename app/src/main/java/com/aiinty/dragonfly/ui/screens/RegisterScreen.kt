package com.aiinty.dragonfly.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.ui.components.BaseButton
import com.aiinty.dragonfly.ui.components.BaseHeader
import com.aiinty.dragonfly.ui.components.DefaultHeader
import com.aiinty.dragonfly.ui.theme.Outline
import com.aiinty.dragonfly.ui.theme.Primary
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import com.aiinty.dragonfly.ui.theme.Secondary
import com.aiinty.dragonfly.ui.theme.SecondaryContainer
import kotlinx.serialization.Serializable

@Serializable
object Register

enum class RegisterScreenState {
    START, EMAIL, USERNAME, PASSWORD;
//    PASSCODE // TODO

    fun calculatePercent(): Float = this.ordinal / RegisterScreenState.entries.size.toFloat()
}

private data class RegisterItem(
    val titleId: Int?,
    val descId: Int?,
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    RegisterScreen(
        User(),
        RegisterScreenState.EMAIL,
        onNextNavigate = { }
    )
}

@Composable
fun RegisterScreen(
    user: User, startState: RegisterScreenState = RegisterScreenState.START,
    onNextNavigate: (User) -> Unit
) {
    val items = mapOf(
        RegisterScreenState.START to RegisterItem(R.string.register, R.string.register_desc),
        RegisterScreenState.EMAIL to RegisterItem(
            R.string.register_email, R.string.register_email_desc
        ),
        RegisterScreenState.USERNAME to RegisterItem(
            R.string.register_username, R.string.register_username_desc
        ),
        RegisterScreenState.PASSWORD to RegisterItem(
            R.string.register_password, R.string.register_password_desc
        ),
    )

    val screenState = remember { mutableStateOf(startState) }

    fun toPrevState() {
        if (screenState.value.ordinal != 0) {
            screenState.value = RegisterScreenState.entries[screenState.value.ordinal - 1]
        }
    }

    fun toNextState() {
        if (screenState.value.ordinal != RegisterScreenState.entries.size - 1) {
            screenState.value = RegisterScreenState.entries[screenState.value.ordinal + 1]
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        AnimatedContent(
            targetState = screenState.value, transitionSpec = {
                fadeIn().togetherWith(fadeOut())
            }, label = "header"
        ) { state ->
            when (state) {
                RegisterScreenState.START -> DefaultHeader()
                else -> RegisterHeader(state,
                    onBackClick = { toPrevState() },
                    onInformationClick = { })
            }
        }

        Column(
            modifier = Modifier.padding(10.dp, 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            AnimatedContent(
                targetState = screenState.value, transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                }, label = "itemsAnimation"
            ) { state ->
                val item = items[state]
                item?.let { _ ->
                    when (state) {
                        RegisterScreenState.START -> {
                            ItemDetails(item.titleId, item.descId) {
                                StartCard(onRegisterClick = { toNextState() })
                            }
                        }
                        RegisterScreenState.EMAIL -> {
                            ItemDetails(item.titleId, item.descId) {
                                EmailCard(user, onNextClick = {
                                    toNextState()
                                })
                            }
                        }

                        RegisterScreenState.USERNAME -> {
                            ItemDetails(item.titleId, item.descId) {
                                UsernameCard(user, onNextClick = { toNextState() })
                            }
                        }

                        RegisterScreenState.PASSWORD -> {
                            ItemDetails(item.titleId, item.descId) {
                                PasswordCard(user, onNextClick = {
                                    onNextNavigate(user)
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StartCard(
    onRegisterClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { },
            Modifier
                .fillMaxWidth()
                .height(53.dp),
            shape = RoundedCornerShape(8.dp),
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

        Button(
            onClick = { onRegisterClick() },
            Modifier
                .fillMaxWidth()
                .height(53.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
        ) {
            Text(
                text = stringResource(R.string.register_with_email),
                style = MaterialTheme.typography.labelSmall,
                color = Primary
            )
        }
    }
}

@Composable
private fun EmailCard(
    user: User, onNextClick: () -> Unit
) {
    val email = remember { mutableStateOf(user.email ?: "") }
    val isCorrect = remember { mutableStateOf(false) }

    LaunchedEffect(email.value) {
        isCorrect.value = android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    Column(
        modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = email.value,
            singleLine = true,
            onValueChange = {
                isCorrect.value = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                email.value = it
            },
            label = { Text(text = stringResource(id = R.string.register_email)) })


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

@Composable
private fun UsernameCard(
    user: User, onNextClick: () -> Unit
) {
    val username = remember { mutableStateOf(user.username ?: "") }
    val isCorrect = remember { mutableStateOf(false) }

    LaunchedEffect(username.value) {
        isCorrect.value = username.value.isNotEmpty() && username.value.takeLast(1).contains(Regex("[A-Za-z_]"))
    }

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = username.value,
            singleLine = true,
            onValueChange = {
                if (it.takeLast(1).contains(Regex("[A-Za-z_]"))) {
                    username.value = it
                }
                isCorrect.value = username.value.isNotEmpty()
            },
            label = { Text(text = stringResource(id = R.string.register_username)) }
        )
        BaseButton(
            onClick = { user.username = username.value; onNextClick();  }, enabled = isCorrect.value
        ) {
            Text(
                text = stringResource(R.string.register_next),
                style = MaterialTheme.typography.labelSmall,
                color = Primary
            )
        }
    }
}

@Composable
private fun PasswordCard(
    user: User,
    onNextClick: () -> Unit
) {
    val password = remember { mutableStateOf("") }
    val passwordRe = remember { mutableStateOf("") }

    val isCorrect = remember { mutableStateOf(false) }

    LaunchedEffect(password.value, passwordRe.value) {
        isCorrect.value = password.value == passwordRe.value && password.value.isNotEmpty()
    }

    Column(
        modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = password.value,
                singleLine = true,
                onValueChange = { password.value = it; isCorrect.value = (password.value == passwordRe.value) },
                label = { Text(text = stringResource(id = R.string.register_password)) })

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = passwordRe.value,
                singleLine = true,
                onValueChange = { passwordRe.value = it; isCorrect.value = (password.value == passwordRe.value) },
                label = { Text(text = stringResource(id = R.string.register_password_re)) })
        }

        BaseButton(
            onClick = {  user.password = password.value; onNextClick() }, enabled = isCorrect.value
        ) {
            Text(
                text = stringResource(R.string.register_next),
                style = MaterialTheme.typography.labelSmall,
                color = Primary
            )
        }
    }
}

@Composable
private fun RegisterHeader(
    state: RegisterScreenState,
    onBackClick: () -> Unit,
    onInformationClick: () -> Unit,
) {
    Column {
        BaseHeader({
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                    contentDescription = stringResource(R.string.arrow)
                )
            }
        }, {
            Text(
                text = stringResource(R.string.register),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.W600
            )
        }, {
            IconButton(onClick = { onInformationClick() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.info_circle),
                    contentDescription = stringResource(R.string.arrow)
                )
            }
        })
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = state.calculatePercent(),
            color = PrimaryContainer
        )
    }
}

@Composable
private fun ItemDetails(
    titleId: Int?, descId: Int?, content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = titleId?.let { stringResource(titleId) } ?: "",
                style = MaterialTheme.typography.titleMedium)
            Text(text = descId?.let { stringResource(descId) } ?: "",
                style = MaterialTheme.typography.bodySmall)
        }
        content()
    }
}
