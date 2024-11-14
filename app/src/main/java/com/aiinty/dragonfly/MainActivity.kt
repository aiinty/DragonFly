package com.aiinty.dragonfly

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aiinty.dragonfly.ui.rememberAppState
import com.aiinty.dragonfly.ui.theme.DragonFlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()

        setContent {
            val appState = rememberAppState()

            DragonFlyTheme {
                DragonFlyApp(appState)
            }
        }
    }
}
