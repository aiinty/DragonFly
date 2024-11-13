package com.aiinty.dragonfly.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.ui.theme.Gray
import com.aiinty.dragonfly.ui.theme.Outline
import com.aiinty.dragonfly.ui.theme.Primary
import com.aiinty.dragonfly.ui.theme.PrimaryContainer

@Composable
fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth().height(53.dp),
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = PrimaryContainer,
        contentColor = Primary,
        disabledContainerColor = Outline,
        disabledContentColor = Gray
    ),
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border
    ) {
        content()
    }
}