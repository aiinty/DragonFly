package com.aiinty.dragonfly.ui.screens.welcome.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.components.AnimatedLinearProgressIndicator
import com.aiinty.dragonfly.ui.components.BaseHeader

@Composable
fun RegisterHeader(
    modifier: Modifier = Modifier,
    state: RegisterScreenState,
    previousState: RegisterScreenState = state.previousState(),
    onBackClick: () -> Unit,
    onInformationClick: () -> Unit,
) {
    Column {
        BaseHeader(
            modifier = modifier,
            {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                        contentDescription = stringResource(R.string.arrow)
                    )
                }
            },
            {
                Text(
                    text = stringResource(R.string.register),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.W600
                )
            },
            {
                IconButton(
                    onClick = { onInformationClick() }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.info_circle),
                        contentDescription = stringResource(R.string.arrow)
                    )
                }
            }
        )

        AnimatedLinearProgressIndicator(
            startProgress = previousState.calculatePercent(),
            targetProgress = state.calculatePercent(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterHeaderPreview() {
    RegisterHeader (
        state = RegisterScreenState.USERNAME,
        onBackClick = {}
    ) { }
}