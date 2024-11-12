package com.aiinty.dragonfly

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.ui.screens.Auth
import com.aiinty.dragonfly.ui.screens.AuthScreen
import com.aiinty.dragonfly.ui.screens.Loading
import com.aiinty.dragonfly.ui.screens.LoadingScreen
import com.aiinty.dragonfly.ui.screens.Login
import com.aiinty.dragonfly.ui.screens.LoginScreen
import com.aiinty.dragonfly.ui.screens.Onboarding
import com.aiinty.dragonfly.ui.screens.OnboardingScreen
import com.aiinty.dragonfly.ui.theme.DragonFlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            DragonFlyApp()
        }
    }
}

@Preview
@Composable
fun DragonFlyApp(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = Loading
){
    DragonFlyTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable<Loading> {
                    LoadingScreen(
                        onUserCredentialsFound = { user ->
                            navController.navigate(Auth(user)) {
                                launchSingleTop = true
                            }
                        },
                        onUserCredentialsNotFound = {
                            navController.navigate(Onboarding) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable<Onboarding> {
                    OnboardingScreen(onNavigateToNext = {
                        navController.navigate(route = Login) {
                            launchSingleTop = true
                        }
                    })
                }
                composable<Login> {
                    LoginScreen()
                }
                composable<Auth> { backStack ->
                    val user = backStack.toRoute<User>()
                    AuthScreen(user)
                }
            }
        }
    }
}
