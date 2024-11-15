package com.aiinty.dragonfly.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BaseHeader(
    modifier: Modifier = Modifier,
    vararg items: @Composable () -> Unit
) {
    Surface {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (items.size == 1) Arrangement.Center else Arrangement.SpaceBetween,
        ) {
            items.forEach { it() }
        }
    }
}