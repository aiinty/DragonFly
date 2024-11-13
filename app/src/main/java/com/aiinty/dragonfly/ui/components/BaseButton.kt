package com.aiinty.dragonfly.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.ui.theme.PrimaryContainer

@Composable
fun BaseButton(
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(PrimaryContainer),
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(53.dp),
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = colors,
        enabled = enabled
    ) {
        content()
    }
}