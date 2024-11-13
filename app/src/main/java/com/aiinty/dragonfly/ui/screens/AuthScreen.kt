package com.aiinty.dragonfly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import com.aiinty.dragonfly.ui.theme.Secondary
import kotlinx.serialization.Serializable

@Serializable
object Auth

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    AuthScreen(
        User()
    ) {}
}

@Composable
fun AuthScreen(
    user: User,
    onNextNavigate: () -> Unit
) {
    var passCode by remember { mutableStateOf("") }

    Row(
        Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 16.dp),
            verticalArrangement = Arrangement.spacedBy(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(26.dp)
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.lock_icon),
                    contentDescription = "Lock Icon",
                    tint = Color(0xFF05BE71)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.auth_enter_code),
                        style = MaterialTheme.typography.labelMedium
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(4) { index ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(
                                        1.dp, if (index >= passCode.length)
                                            Color(0xFFECECEC) else
                                            PrimaryContainer, shape = RoundedCornerShape(10.dp)
                                    )
                                    .size(48.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (index < passCode.length) {
                                    Image(
                                        imageVector = ImageVector.vectorResource(R.drawable.ellipse),
                                        "Code cell",
                                        Modifier.size(8.dp, 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val keys = listOf(
                    listOf("1", "4", "7", ""),
                    listOf("2", "5", "8", "0"),
                    listOf("3", "6", "9", "<")
                )

                Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {

                    keys.forEach { column ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)

                        ) {
                            column.forEach { key ->
                                if (key.isNotEmpty()) {

                                    KeyboardButton(key, onClick = {

                                        when (key) {
                                            "<" -> {
                                                if (passCode.isNotEmpty()) {
                                                    passCode = passCode.dropLast(1)
                                                }
                                            }

                                            else -> {
                                                if (passCode.length < 4) {
                                                    passCode += key
                                                }

                                                if (passCode == user.passCode) {
                                                    onNextNavigate()
                                                }
                                            }
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardButton(key: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick, modifier = Modifier
            .size(64.dp)
            .background(Color.Transparent)
    ) {
        if (key == "<") {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.backspace),
                "Backspace button",
                Modifier
                    .size(32.dp, 32.dp)
                    .padding(end = 8.dp)
            )
        } else {
            Text(
                key, style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 24.sp, color = Secondary, lineHeight = 32.sp
                )
            )
        }
    }
}
