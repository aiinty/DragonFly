package com.aiinty.dragonfly

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aiinty.dragonfly.ui.AppState
import com.aiinty.dragonfly.ui.TopAppBarStateProvider
import com.aiinty.dragonfly.ui.navigation.AppNavHost
import com.aiinty.dragonfly.ui.navigation.AppNavigationBottomBar
import com.aiinty.dragonfly.ui.rememberAppState

@Composable
fun DragonFlyApp(
    appState: AppState,
//    navController: NavHostController = rememberNavController(),
//    startDestination: Any = Loading
) {
    val currentDestination = appState.currentDestination

//    val context = LocalContext.current
//    val userViewModel: UserViewModel = viewModel()
//
//    LaunchedEffect(Unit) {
//        userViewModel.loadUser(context)
//    }

    Scaffold(
        topBar = {
            TopAppBarStateProvider.topAppBarState.topBar()
        },
        bottomBar = {
            AnimatedVisibility(
                visible = appState.shouldShowBottomBar
            ) {
                AppNavigationBottomBar(
                    appState = appState,
                )
            }
        },
    ) { innerPadding ->
        AppNavHost(
            appState = appState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun DragonFlyAppPreview() {
    DragonFlyApp(
        rememberAppState(),
    )
}
