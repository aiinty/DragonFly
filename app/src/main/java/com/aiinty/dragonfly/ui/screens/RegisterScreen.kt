package com.aiinty.dragonfly.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.R
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

private enum class RegisterScreenState() {
    START,
    EMAIL,
    USERNAME,
    PASSWORD;
//    PASSCODE // TODO

    fun calculatePercent(): Float = this.ordinal / RegisterScreenState.entries.size.toFloat()
}

private data class RegisterItem(
    val titleId: Int?,
    val descId: Int?,
)

@Composable
fun RegisterScreen() {
    val context = LocalContext.current
    val items = mapOf(
        RegisterScreenState.START to RegisterItem(R.string.register, R.string.register_desc),
        RegisterScreenState.EMAIL to RegisterItem(R.string.register_email, R.string.register_email_desc),
        RegisterScreenState.USERNAME to RegisterItem(R.string.register_username, R.string.register_username_desc),
        RegisterScreenState.PASSWORD to RegisterItem(R.string.register_password, R.string.register_password_desc),
    )
    val screenState = remember { mutableStateOf(RegisterScreenState.PASSWORD) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        AnimatedContent(
            targetState = screenState.value,
            label = "itemsAnimation"
        ) { state ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val item = items[state]
                item?.let {
                    when(state) {
                        RegisterScreenState.EMAIL -> {
                            RegisterHeader(state)
                        }
                        RegisterScreenState.USERNAME -> {
                            RegisterHeader(state)
                        }
                        RegisterScreenState.PASSWORD -> {
                            RegisterHeader(state)
                        }

                        else -> {
                            DefaultHeader()

                            ItemDetails(item.titleId, item.descId) {
                                StartCard()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StartCard() {
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
            Row (
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
            onClick = { },
            Modifier
                .fillMaxWidth()
                .height(53.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
        ) {
            Text(
                text = stringResource(R.string.register_email),
                style = MaterialTheme.typography.labelSmall,
                color = Primary
            )
        }
    }
}

@Composable
private fun RegisterHeader(
    state: RegisterScreenState
) {
    Column {
        BaseHeader(
            {
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                        contentDescription = stringResource(R.string.arrow)
                    )
                }
            },
            {
                Text(
                    text = stringResource(R.string.register),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            {
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.info_circle),
                        contentDescription = stringResource(R.string.arrow)
                    )
                }
            }
        )
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = state.calculatePercent(),
            color = PrimaryContainer
        )
    }
}

@Composable
private fun ItemDetails(
    titleId: Int?,
    descId: Int?,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = titleId?.let { stringResource(titleId) } ?: "",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = descId?.let { stringResource(descId) } ?: "",
            style = MaterialTheme.typography.bodySmall
        )
        content()
    }
}
