package com.aiinty.dragonfly.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun ColumnScope.AnimateInvisibility(
    visible: Boolean,
    enter: EnterTransition = fadeIn() + expandVertically(),
    exit: ExitTransition = fadeOut() + shrinkVertically(),
    label: String = "AnimatedInvisibility",
    modifier: Modifier = Modifier,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Box(modifier) {
        this@AnimateInvisibility.AnimatedVisibility(
            visible = visible,
            enter = enter,
            exit = exit,
            label = label
        ) {
            content(modifier = Modifier)
        }
        // Note: doesn't work well if you have clickable objects underneath
        content(modifier = Modifier.graphicsLayer { alpha = 0f })
    }
}