package com.aiinty.dragonfly.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aiinty.dragonfly.ui.theme.PrimaryContainer

@Composable
fun BaseLinearProgressIndicator(
    modifier: Modifier = Modifier.fillMaxWidth(),
    progress: Float
) {
    LinearProgressIndicator(
        modifier = modifier,
        progress = progress,
        color = PrimaryContainer
    )
}