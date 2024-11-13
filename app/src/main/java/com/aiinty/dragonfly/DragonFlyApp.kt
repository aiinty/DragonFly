package com.aiinty.dragonfly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aiinty.dragonfly.core.datastore.DataStoreInstance
import com.aiinty.dragonfly.core.entity.USER_KEY
import com.aiinty.dragonfly.core.entity.User
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
import kotlinx.coroutines.runBlocking

@Preview
@Composable
fun DragonFlyApp(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = Loading
) {
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable<Loading> {
                LoadingScreen(

                    onUserCredentialsFound = { user ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(USER_KEY, user)
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
                        navController.currentBackStackEntry?.savedStateHandle?.set(USER_KEY, user)
                        navController.navigate(Onboarding){
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Onboarding> {
                val userObject: User? = navController.previousBackStackEntry?.savedStateHandle?.get(
                    USER_KEY
                )

                OnboardingScreen(
                    userObject!!,
                    onNavigateToNext = { user ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(USER_KEY, user)
                        navController.navigate(Login)
                    })
            }
            composable<Login> {
                val userObject: User? = navController.previousBackStackEntry?.savedStateHandle?.get(
                    USER_KEY
                )

                LoginScreen(
                    userObject!!,
                    onSuccessfulLogin = { user ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(USER_KEY, user)
                        navController.navigate(Auth)
                    },
                    onRegisterNavigation = { user ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(USER_KEY, user)
                        navController.navigate(Register)
                    }
                )
            }
            composable<Auth> {
                val userObject: User? = navController.previousBackStackEntry?.savedStateHandle?.get(
                    USER_KEY
                )

                AuthScreen(userObject!!, {

                })
            }


            composable<Register> {
                val userObject: User? = navController.previousBackStackEntry?.savedStateHandle?.get(
                    USER_KEY
                )

                RegisterScreen(userObject!!, onNextNavigate = { user ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(USER_KEY, user)
                    user.passCode = "1111"
                    user.isRegistered = true
                    runBlocking {
                        DataStoreInstance.writeUser(context, user)
                    }
                    navController.navigate(Auth) {
                        launchSingleTop = true
                    }
                })

            }

            composable<Profile> {
                val userObject: User? = navController.previousBackStackEntry?.savedStateHandle?.get(
                    USER_KEY
                )

                ProfileScreen(userObject!!) // TODO
            }

        }
    }
}

