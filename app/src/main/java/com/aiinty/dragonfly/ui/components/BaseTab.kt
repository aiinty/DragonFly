package com.aiinty.dragonfly.ui.components

import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import com.aiinty.dragonfly.ui.theme.Secondary


@Composable
fun BaseTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    selectedContentColor: Color = PrimaryContainer,
    unselectedContentColor: Color = Secondary
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        text = text,
        icon = icon,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
    )
}