package com.aiinty.dragonfly.ui.screens.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.TopAppBarState
import com.aiinty.dragonfly.ui.TopAppBarStateProvider
import kotlinx.serialization.Serializable

@Serializable
object PocketRoute

@Composable
fun PocketScreen() {
    TopAppBarStateProvider.update(
        TopAppBarState {
            HomeHeader()
        }
    )

    Text(stringResource(R.string.pocket))
}

fun NavController.navigateToPocket(navOptions: NavOptions) =
    navigate(route = PocketRoute, navOptions)

fun NavGraphBuilder.pocketScreen() {
    composable<PocketRoute> {
        PocketScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun PocketPreview() {
    PocketScreen()
}