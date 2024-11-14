package com.aiinty.dragonfly.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun AnimatedLinearProgressIndicator(
    modifier: Modifier = Modifier,
    startProgress: Float,
    targetProgress: Float,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    var progress by remember { mutableStateOf(startProgress) }
    val progressAnimDuration = 1_500
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
    )

    BaseLinearProgressIndicator(
        progress = progressAnimation,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
    )

    LaunchedEffect(lifecycleOwner) {
        progress = targetProgress
    }
}