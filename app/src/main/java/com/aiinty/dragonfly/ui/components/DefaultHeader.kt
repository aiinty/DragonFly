package com.aiinty.dragonfly.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.R

@Composable
fun DefaultHeader() {
    BaseHeader(
        {
            Image(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.logo),
                colorFilter = ColorFilter.tint(Color(0xFF202020)),
                contentDescription = stringResource(R.string.app_name),
            )
        },
        {
            Image(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.translate),
                contentDescription = stringResource(R.string.app_name),
            )
        }
    )
}