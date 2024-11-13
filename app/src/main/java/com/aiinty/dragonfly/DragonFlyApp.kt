package com.aiinty.dragonfly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aiinty.dragonfly.core.datastore.DataStoreInstance
import com.aiinty.dragonfly.core.entity.USER_KEY
import com.aiinty.dragonfly.ui.screens.Auth
import com.aiinty.dragonfly.ui.screens.AuthScreen
import com.aiinty.dragonfly.ui.screens.Loading
import com.aiinty.dragonfly.ui.screens.LoadingScreen
import com.aiinty.dragonfly.ui.screens.Login
import com.aiinty.dragonfly.ui.screens.LoginScreen
import com.aiinty.dragonfly.ui.screens.Onboarding
import com.aiinty.dragonfly.ui.screens.OnboardingScreen
import com.aiinty.dragonfly.ui.screens.Profile
import com.aiinty.dragonfly.ui.screens.ProfileScreen
import com.aiinty.dragonfly.ui.screens.Register
import com.aiinty.dragonfly.ui.screens.RegisterScreen
import com.aiinty.dragonfly.ui.viewmodel.UserViewModel
import kotlinx.coroutines.runBlocking

@Preview
@Composable
fun DragonFlyApp(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = Loading
) {
    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel()

    LaunchedEffect(Unit) {
        userViewModel.loadUser(context)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable<Loading> {
                LoadingScreen(
                    onUserCredentialsFound = { user ->
                        userViewModel.updateUser(user)
                        if (user.rememberMe) {
                            navController.navigate(Auth) {
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate(Login) {
                                launchSingleTop = true
                            }
                        }
                    },
                    onUserCredentialsNotFound = { user ->
                        userViewModel.updateUser(user)

                        navController.navigate(Onboarding){
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Onboarding> {
                val userObject = userViewModel.user

                OnboardingScreen(
                    userObject!!,
                    onNavigateToNext = { user ->
                        userViewModel.updateUser(user)
                        navController.navigate(Login)
                    })
            }
            composable<Login> {
                val userObject = userViewModel.user

                LoginScreen(
                    userObject!!,
                    onSuccessfulLogin = { user ->
                        userViewModel.updateUser(user)
                        navController.navigate(Auth)
                    },
                    onRegisterNavigation = { user ->
                        userViewModel.updateUser(user)
                        navController.navigate(Register)
                    }
                )
            }
            composable<Auth> {
                val userObject = userViewModel.user

                AuthScreen(userObject!!, {
                })
            }


            composable<Register> {
                val userObject = userViewModel.user

                RegisterScreen(userObject!!, onNextNavigate = { user ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(USER_KEY, user)
                    user.passCode = "1111"
                    user.isRegistered = true
                    runBlocking {
                        DataStoreInstance.writeUser(context, user)
                    }
                    userViewModel.updateUser(user)
                    navController.navigate(Auth) {
                        launchSingleTop = true
                    }
                })

            }

            composable<Profile> {
                val userObject = userViewModel.user
                ProfileScreen(userObject!!) // TODO
            }

        }
    }
}

