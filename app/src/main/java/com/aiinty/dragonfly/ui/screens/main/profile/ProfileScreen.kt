package com.aiinty.dragonfly.ui.screens.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.repositories.FakeUserRepository
import com.aiinty.dragonfly.ui.TopAppBarState
import com.aiinty.dragonfly.ui.TopAppBarStateProvider
import com.aiinty.dragonfly.ui.components.BaseButton
import com.aiinty.dragonfly.ui.components.BaseHeader
import com.aiinty.dragonfly.ui.theme.Primary
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onLogOut: () -> Unit
) {
    LaunchedEffect(Unit) {
        TopAppBarStateProvider.update(
            TopAppBarState {
                ProfileHeader()
            }
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
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
                    // FIXME
                    var name = ""
                    runBlocking {
                        name = profileViewModel.getUser()!!.username
                    }
                    Text(name)
                    Text(stringResource(id = R.string.profile_silver_members))
                }
            }

            Spacer(Modifier.size(10.dp))
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

        BaseButton(
            onClick = {
                runBlocking {
                    val user = profileViewModel.getUser()
                    user!!.rememberMe = false
                    profileViewModel.saveUser(user)
                    onLogOut()
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFFE324E))
        ) {
            Text(
                text = stringResource(R.string.profile_log_out),
                style = MaterialTheme.typography.labelSmall,
                color = Primary
            )
        }
    }
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
            .clip(RoundedCornerShape(4.dp))
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

@Composable
private fun ProfileHeader(
    modifier: Modifier = Modifier,
) {
    BaseHeader(
        modifier = modifier.padding(8.dp),
        {
            Text(
                text = stringResource(R.string.profile),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.W500
            )
        }
    )
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

fun NavController.navigateToProfile(navOptions: NavOptions) =
    navigate(route = ProfileRoute, navOptions)

fun NavGraphBuilder.profileScreen(
    onLogOut: () -> Unit
) {
    composable<ProfileRoute> {
        ProfileScreen(
            onLogOut = onLogOut
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileHeaderPreview() {
    ProfileHeader()
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    ProfileScreen(
        profileViewModel = ProfileViewModel(FakeUserRepository()),
        {}
    )
}
