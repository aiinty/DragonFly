package com.aiinty.dragonfly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.ui.components.BaseButton
import com.aiinty.dragonfly.ui.components.BaseHeader
import com.aiinty.dragonfly.ui.theme.Primary
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import kotlinx.serialization.Serializable

@Serializable
object Profile

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    ProfileScreen(
        User("Example", "Example", "Example", "1234", true)
    )
}

@Composable
fun ProfileScreen(
    user: User
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        ProfileHeader()

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            )

            {
                Image(
                    painterResource(id = R.drawable.avatar), contentDescription = stringResource(
                        R.string.user_avatar
                    ), Modifier.clip(
                        CircleShape
                    )
                )

                Column {
                    Text(user.username!!)
                    Text(stringResource(id = R.string.profile_silver_members))
                }
            }

            ProfileMenuItem(text = { Text(stringResource(id = R.string.personal_data)) }, icon = {
                Icon(
                    ImageVector.vectorResource(R.drawable.personalcard_icon),
                    stringResource(id = R.string.personal_data),
                    Modifier.size(20.dp),
                    tint = PrimaryContainer
                )
            }, onClick = { })

            ProfileMenuItem(text = { Text("Settings") }, icon = {
                Icon(
                    ImageVector.vectorResource(R.drawable.setting_icon),
                    "settings",
                    Modifier.size(20.dp),
                    tint = PrimaryContainer
                )
            }, onClick = { })
        }
            BaseButton(onClick = {}, colors = ButtonDefaults.buttonColors(Color(0xFFFE324E))) {
                Text(
                    text = stringResource(R.string.profile_log_out),
                    style = MaterialTheme.typography.labelSmall,
                    color = Primary
                )
            }

    }
}

@Composable
private fun ProfileHeader() {
    BaseHeader({
        Text(text = stringResource(id = R.string.profile))
    })
}

@Composable
private fun ProfileMenuItem(
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        icon()

        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text()

                Icon(ImageVector.vectorResource(R.drawable.arrow_right), "arrow right")

            }

            Divider(
                Modifier.fillMaxWidth(), 2.dp
            )
        }
    }
}