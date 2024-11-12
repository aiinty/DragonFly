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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.core.entity.User
import kotlinx.serialization.Serializable

@Serializable
object Profile

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
        Text("Profile")

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement
                .spacedBy(30.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically)

            {
                Image(painter = painterResource(R.drawable.avatar), contentDescription = "User avatar", Modifier.clip(
                    CircleShape))

                Column {
                    Text("Username")
                    Text("member")
                }
            }

            ProfileMenuItem(
                text = { Text("Personal data", ) },
                icon = { Icon(ImageVector.vectorResource(R.drawable.personalcard_icon),
                    "personal data",
                    Modifier.size(20.dp)
                    )
                },
                onClick = { }
                )

            ProfileMenuItem(
                text = { Text("Settings", ) },
                icon = { Icon(ImageVector.vectorResource(R.drawable.setting_icon),
                    "settings",
                    Modifier.size(20.dp)
                )
                },
                onClick = { }
            )
        }
    }
}

@Composable
fun ProfileMenuItem(text: @Composable () -> Unit, icon: @Composable () -> Unit, onClick: () -> Unit) {
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
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text()

                Icon(ImageVector.vectorResource(R.drawable.arrow_right), "arrow right")

            }

            Divider(
                Modifier.fillMaxWidth(),
                2.dp
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(User("Example", "Example", "1234", true))
}