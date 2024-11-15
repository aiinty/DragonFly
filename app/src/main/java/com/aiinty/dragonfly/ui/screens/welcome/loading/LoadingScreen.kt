package com.aiinty.dragonfly.ui.screens.welcome.loading

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
object LoadingRoute

@Composable
fun LoadingScreen(
    loadingViewModel: LoadingViewModel = hiltViewModel(),
    onUserCredentialsFoundRemembered: () -> Unit,
    onUserCredentialsFoundNotRemembered: () -> Unit,
    onUserCredentialsNotFound: () -> Unit,
) {
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
    }

    LaunchedEffect(Unit) {
        delay(3000)

        val user = loadingViewModel.getUser()

        if (user == null) {
            onUserCredentialsNotFound()
            return@LaunchedEffect
        }
        if (user.rememberMe) {
            onUserCredentialsFoundRemembered()
            return@LaunchedEffect
        }
        onUserCredentialsFoundNotRemembered()
    }
}

fun NavController.navigateToLoading(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = LoadingRoute, navOptions)

fun NavGraphBuilder.loadingScreen(
    onUserCredentialsFoundRemembered: () -> Unit,
    onUserCredentialsFoundNotRemembered: () -> Unit,
    onUserCredentialsNotFound: () -> Unit,
) {
    composable<LoadingRoute> {
        LoadingScreen(
            onUserCredentialsFoundRemembered = onUserCredentialsFoundRemembered,
            onUserCredentialsFoundNotRemembered = onUserCredentialsFoundNotRemembered,
            onUserCredentialsNotFound = onUserCredentialsNotFound
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    LoadingScreen(
        onUserCredentialsFoundRemembered = { },
        onUserCredentialsFoundNotRemembered = { },
        onUserCredentialsNotFound = { },
    )
}
