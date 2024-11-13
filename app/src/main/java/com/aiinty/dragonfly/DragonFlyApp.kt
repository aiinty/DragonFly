package com.aiinty.dragonfly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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

    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination,
        ) {
            composable<Loading> {
                LoadingScreen(
                    onUserCredentialsFound = { user ->
                        userViewModel.updateUser(user)

                        // FIXME: navigate without transition???????? 
                        navController.popBackStack()
                        if (user.rememberMe) {
                            navController.navigate(Auth)
                        } else {
                            navController.navigate(Login)
                        }
                    },
                    onUserCredentialsNotFound = { user ->
                        userViewModel.updateUser(user)

                        navController.popBackStack()
                        navController.navigate(Onboarding)
                    }
                )
            }

            composable<Onboarding> {
                OnboardingScreen(
                    userViewModel.user,
                    onNavigateToNext = { user ->
                        userViewModel.updateUser(user)
                        navController.navigate(Login)
                    })
            }

            composable<Login> {
                LoginScreen(
                    userViewModel.user,
                    onSuccessfulLogin = { user ->
                        userViewModel.updateUser(user)

                        navController.popBackStack()
                        navController.navigate(Auth)
                    },
                    onRegisterNavigation = { user ->
                        userViewModel.updateUser(user)
                        navController.navigate(Register)
                    }
                )
            }

            composable<Auth> {
                AuthScreen(
                    userViewModel.user,
                    { }
                ) // TODO
            }

            composable<Register> {
                RegisterScreen(
                    userViewModel.user,
                    onNextNavigate = { user ->
                    user.passCode = "1111" // TODO: delete this shit
                    runBlocking {
                        DataStoreInstance.writeUser(context, user)
                    }
                    userViewModel.updateUser(user)

                    navController.popBackStack()
                    navController.navigate(Auth)
                })

            }

            composable<Profile> {
                ProfileScreen(
                    userViewModel.user
                ) // TODO
            }
        }
    }
}
