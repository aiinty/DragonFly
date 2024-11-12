package com.aiinty.dragonfly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.core.datastore.DataStoreInstance
import com.aiinty.dragonfly.core.entity.USER_LOGIN_KEY
import com.aiinty.dragonfly.core.entity.USER_PASSCODE_KEY
import com.aiinty.dragonfly.core.entity.USER_PASSWORD_KEY
import com.aiinty.dragonfly.core.entity.USER_REMEMBER_ME_KEY
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable

@Serializable
object Loading

@Composable
fun LoadingScreen(
    onUserCredentialsFound: (User) -> Unit,
    onUserCredentialsNotFound: (User) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.logo),
            contentDescription = stringResource(R.string.app_name),
            colorFilter = ColorFilter.tint(PrimaryContainer)
        )
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleMedium,
            color = PrimaryContainer
        )

        LaunchedEffect(Unit) {
            delay(3000)

            val login = DataStoreInstance.readStringValue(context, USER_LOGIN_KEY).first()
            val password = DataStoreInstance.readStringValue(context, USER_PASSWORD_KEY).first()
            val passcode = DataStoreInstance.readStringValue(context, USER_PASSCODE_KEY).first()
            val rememberMe = DataStoreInstance.readBooleanValue(context, USER_REMEMBER_ME_KEY).first()

            val user = User(login, password, passcode, rememberMe)

            if (login != null && password != null && passcode != null && rememberMe != null) {
                onUserCredentialsFound(user)
                return@LaunchedEffect
            }
            onUserCredentialsNotFound(user)
        }
    }
}
