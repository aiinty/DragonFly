package com.aiinty.dragonfly.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.R

@Preview(showBackground = true)
@Composable
fun DefaultHeader() {
    BaseHeader(
        {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.logo),
                    tint = Color(0xFF202020),
                    contentDescription = stringResource(R.string.app_name),
                )
            }
        },
        {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.translate),
                    contentDescription = stringResource(R.string.app_name),
                )
            }
        },
    )
}