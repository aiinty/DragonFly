package com.aiinty.dragonfly.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.aiinty.dragonfly.ui.AppState
import com.aiinty.dragonfly.ui.screens.main.homeScreen
import com.aiinty.dragonfly.ui.screens.main.inboxScreen
import com.aiinty.dragonfly.ui.screens.main.pocketScreen
import com.aiinty.dragonfly.ui.screens.main.profile.profileScreen
import com.aiinty.dragonfly.ui.screens.welcome.auth.authScreen
import com.aiinty.dragonfly.ui.screens.welcome.auth.navigateToAuth
import com.aiinty.dragonfly.ui.screens.welcome.information.informationScreen
import com.aiinty.dragonfly.ui.screens.welcome.information.navigateToInformation
import com.aiinty.dragonfly.ui.screens.welcome.loading.LoadingRoute
import com.aiinty.dragonfly.ui.screens.welcome.loading.loadingScreen
import com.aiinty.dragonfly.ui.screens.welcome.login.LoginRoute
import com.aiinty.dragonfly.ui.screens.welcome.login.loginScreen
import com.aiinty.dragonfly.ui.screens.welcome.login.navigateToLogin
import com.aiinty.dragonfly.ui.screens.welcome.onboarding.OnboardingRoute
import com.aiinty.dragonfly.ui.screens.welcome.onboarding.navigateToOnboarding
import com.aiinty.dragonfly.ui.screens.welcome.onboarding.onboardingScreen
import com.aiinty.dragonfly.ui.screens.welcome.register.RegisterViewModel
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
    val registerViewModel: RegisterViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = LoadingRoute,
        modifier = modifier,
    ) {
        loadingScreen(
            onUserCredentialsFoundRemembered =
            {
                navController.navigateToAuth {
                    popUpTo(LoadingRoute) {
                        inclusive = true
                    }
                }
            },
            onUserCredentialsFoundNotRemembered =
            {
                navController.navigateToLogin {
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
            onLoginNavigate = { navController.navigateToLogin() }
        )

        loginScreen(
            onSuccessfulLogin =
            {
                navController.navigateToAuth {
                    popUpTo(OnboardingRoute) {
                        inclusive = true
                    }
                }
            },
            onRegisterNavigation = { navController.navigateToRegister() },
            onForgotPassword = { navController.navigateToInformation() }
        )

        registerScreen(
            registerViewModel = registerViewModel,
            onEmailNavigate = { navController.navigateToRegisterEmail() }
        ) {
            registerEmailScreen(
                registerViewModel = registerViewModel,
                onNextNavigate = { navController.navigateToRegisterUsername() },
                onPreviousNavigate = { appState.navigateToPreviousDestination() },
                onInformationNavigate = { navController.navigateToInformation() }
            )
            registerUsernameScreen(
                registerViewModel = registerViewModel,
                onNextNavigate = { navController.navigateToRegisterPassword() },
                onPreviousNavigate = { appState.navigateToPreviousDestination() },
                onInformationNavigate = { navController.navigateToInformation() }
            )
            registerPasswordScreen(
                registerViewModel = registerViewModel,
                onNextNavigate =
                {
                    navController.navigateToAuth {
                        popUpTo(OnboardingRoute) {
                            inclusive = true
                        }
                    }
                },
                onPreviousNavigate = { appState.navigateToPreviousDestination() },
                onInformationNavigate = { navController.navigateToInformation() }
            )
        }

        informationScreen(
            onPreviousNavigate = { appState.navigateToPreviousDestination() }
        )

        authScreen(
            onHomeNavigate = { appState.navigateToTopLevelDestination(TopLevelDestination.HOME) }
        )

        homeScreen()

        pocketScreen()

        inboxScreen()

        profileScreen(
            onLogOut = {
                navController.navigateToLogin {
                    popUpTo(LoginRoute){
                        saveState = false
                        inclusive = false
                    }
                    restoreState = false
                    launchSingleTop = true
                }
            }
        )
    }
}
