package com.aiinty.dragonfly.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.ui.AppState
import com.aiinty.dragonfly.ui.screens.main.homeScreen
import com.aiinty.dragonfly.ui.screens.main.inboxScreen
import com.aiinty.dragonfly.ui.screens.main.pocketScreen
import com.aiinty.dragonfly.ui.screens.main.profileScreen
import com.aiinty.dragonfly.ui.screens.welcome.LoadingRoute
import com.aiinty.dragonfly.ui.screens.welcome.OnboardingRoute
import com.aiinty.dragonfly.ui.screens.welcome.authScreen
import com.aiinty.dragonfly.ui.screens.welcome.loadingScreen
import com.aiinty.dragonfly.ui.screens.welcome.loginScreen
import com.aiinty.dragonfly.ui.screens.welcome.navigateToAuth
import com.aiinty.dragonfly.ui.screens.welcome.navigateToLogin
import com.aiinty.dragonfly.ui.screens.welcome.navigateToOnboarding
import com.aiinty.dragonfly.ui.screens.welcome.onboardingScreen
import com.aiinty.dragonfly.ui.screens.welcome.register.navigateToRegister
import com.aiinty.dragonfly.ui.screens.welcome.register.navigateToRegisterEmail
import com.aiinty.dragonfly.ui.screens.welcome.register.navigateToRegisterPassword
import com.aiinty.dragonfly.ui.screens.welcome.register.navigateToRegisterUsername
import com.aiinty.dragonfly.ui.screens.welcome.register.registerEmailScreen
import com.aiinty.dragonfly.ui.screens.welcome.register.registerPasswordScreen
import com.aiinty.dragonfly.ui.screens.welcome.register.registerScreen
import com.aiinty.dragonfly.ui.screens.welcome.register.registerUsernameScreen

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = LoadingRoute,
        modifier = modifier,
    ) {
        loadingScreen(
            onUserCredentialsFound =
            {
                navController.navigateToAuth {
                    popUpTo(LoadingRoute) {
                        inclusive = true
                    }
                }
            },
            onUserCredentialsNotFound =
            {
                navController.navigateToOnboarding {
                    popUpTo(LoadingRoute) {
                        inclusive = true
                    }
                }
            }
        )

        onboardingScreen(
            user = User("", "", "", ""),
            onLoginNavigate = { navController.navigateToLogin() }
        )

        loginScreen(
            user = User("", "", "", ""),
            onSuccessfulLogin =
            {
                navController.navigateToAuth {
                    popUpTo(OnboardingRoute) {
                        inclusive = true
                    }
                }
            },
            onRegisterNavigation = { navController.navigateToRegister() }
        )

        registerScreen(
            user = User("", "", "", ""),
            onEmailNavigate = { navController.navigateToRegisterEmail() }
        ) {
            registerEmailScreen(
                user = User("", "", "", ""),
                onNextNavigate = { navController.navigateToRegisterUsername() },
                onPreviousNavigate = { appState.navigateToPreviousDestination() }
            )
            registerUsernameScreen(
                user = User("", "", "", ""),
                onNextNavigate = { navController.navigateToRegisterPassword() },
                onPreviousNavigate = { appState.navigateToPreviousDestination() }
            )
            registerPasswordScreen(
                user = User("", "", "", ""),
                onNextNavigate =
                {
                    navController.navigateToAuth {
                        popUpTo(OnboardingRoute) {
                            inclusive = true
                        }
                    }
                },
                onPreviousNavigate = { appState.navigateToPreviousDestination() }
            )
        }

        authScreen(
            user = User("", "", "", ""),
            onHomeNavigate = { appState.navigateToTopLevelDestination(TopLevelDestination.HOME) }
        )

        homeScreen()

        pocketScreen()

        inboxScreen()

        profileScreen(
            user = User("", "", "", "")
        )
    }
}
