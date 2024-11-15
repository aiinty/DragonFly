package com.aiinty.dragonfly.ui.components

import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.aiinty.dragonfly.ui.theme.Outline

@Composable
fun BaseDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = Outline,
) {
    Divider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}