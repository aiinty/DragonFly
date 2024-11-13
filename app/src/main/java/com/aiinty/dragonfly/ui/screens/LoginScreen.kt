package com.aiinty.dragonfly.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.core.datastore.DataStoreInstance
import com.aiinty.dragonfly.core.entity.USER_REMEMBER_ME_KEY
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.ui.components.DefaultHeader
import com.aiinty.dragonfly.ui.theme.Outline
import com.aiinty.dragonfly.ui.theme.Primary
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import com.aiinty.dragonfly.ui.theme.Secondary
import com.aiinty.dragonfly.ui.theme.SecondaryContainer
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
object Login

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    LoginScreen(
        user = User(),
        onSuccessfulLogin = {},
        onRegisterNavigation = {}
    )
}

@Composable
fun LoginScreen(
    user: User,
    onSuccessfulLogin: (User) -> Unit,
    onRegisterNavigation: (User) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val login = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val isRememberMe = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DefaultHeader()

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
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = login.value,
                singleLine = true,
                onValueChange = { login.value = it },
                label = { Text(text = stringResource(id = R.string.login_email_hint)) }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password.value,
                onValueChange = { password.value = it },
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.login_password_hint)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isRememberMe.value,
                        onCheckedChange = {isRememberMe.value = it}
                    )
                    Text(
                        text = stringResource(id = R.string.login_remember_me),
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
                Text(
                    text = stringResource(id = R.string.login_forgot),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if ((login.value == user.email || login.value == user.username ) && password.value == user.password) {
                        runBlocking {
                            DataStoreInstance.writeBooleanValue(context, USER_REMEMBER_ME_KEY, isRememberMe.value)
                        }
                        onSuccessfulLogin(user)
                    }
                },
                Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.labelSmall,
                    color = Primary
                )
            }

            Text(
                text = "Other",
                modifier = Modifier.padding(vertical = 10.dp),
                style = MaterialTheme.typography.labelSmall,
            )

            Button(
                onClick = {

                },
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
                        text = stringResource(R.string.login_with_gmail),
                        style = MaterialTheme.typography.labelSmall,
                        color = Secondary
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.login_register_hint),
                style = MaterialTheme.typography.labelSmall
                )

            TextButton(
                contentPadding = PaddingValues(0.dp),
                onClick = { onRegisterNavigation(user) }
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    style = MaterialTheme.typography.labelSmall,
                    color = PrimaryContainer
                )
            }
        }
    }
}
