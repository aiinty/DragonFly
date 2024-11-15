package com.aiinty.dragonfly.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.aiinty.dragonfly.ui.navigation.TopLevelDestination
import com.aiinty.dragonfly.ui.screens.main.navigateToHome
import com.aiinty.dragonfly.ui.screens.main.navigateToInbox
import com.aiinty.dragonfly.ui.screens.main.navigateToPocket
import com.aiinty.dragonfly.ui.screens.main.profile.navigateToProfile
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(
        coroutineScope,
        navController,
    ) {
        AppState(
            coroutineScope = coroutineScope,
            navController = navController,
        )
    }
}

data class TopAppBarState(val topBar: @Composable () -> Unit = { })

object TopAppBarStateProvider {
    var topAppBarState: TopAppBarState by mutableStateOf(TopAppBarState())
        private set

    fun update(topAppBarState: TopAppBarState) {
        this.topAppBarState = topAppBarState
    }
}

@Stable
class AppState(
    coroutineScope: CoroutineScope,
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
         @SuppressLint("RestrictedApi") @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            }
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val shouldShowBottomBar: Boolean
        @Composable get() {
            val routeName = navController.currentBackStackEntryAsState().value?.destination?.route
            val topRoutesName = topLevelDestinations.map { it.route.qualifiedName.toString() }
            return routeName in topRoutesName
        }

    fun navigateToPreviousDestination() {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
            navController.navigate(navController.currentBackStackEntry!!.destination.id)
            navController.popBackStack()
        }
    }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                TopLevelDestination.POCKET -> navController.navigateToPocket(topLevelNavOptions)
                TopLevelDestination.INBOX -> navController.navigateToInbox(topLevelNavOptions)
                TopLevelDestination.PROFILE -> navController.navigateToProfile(topLevelNavOptions)
            }
        }
    }
}
