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
import kotlinx.serialization.Serializable

@Serializable
object InboxRoute

@Composable
fun InboxScreen() {
    Text(stringResource(R.string.inbox))
}

fun NavController.navigateToInbox(navOptions: NavOptions) =
    navigate(route = InboxRoute, navOptions)

fun NavGraphBuilder.inboxScreen() {
    composable<InboxRoute> {
        InboxScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun InboxPreview() {
    InboxScreen()
}