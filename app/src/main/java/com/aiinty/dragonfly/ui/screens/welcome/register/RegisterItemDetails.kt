package com.aiinty.dragonfly.ui.screens.welcome.register

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.R

@Composable
fun RegisterItemDetails(
    @StringRes titleId: Int? = null,
    @StringRes descId: Int? = null,
    content: @Composable () -> Unit = { }
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = titleId?.let { stringResource(titleId) } ?: "",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = descId?.let { stringResource(descId) } ?: "",
                style = MaterialTheme.typography.bodySmall
            )
        }
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterItemDetailsPreview() {
    RegisterItemDetails (
        R.string.register_email,
        R.string.register_email_desc
    ) {
        Text("Content here...")
    }
}
