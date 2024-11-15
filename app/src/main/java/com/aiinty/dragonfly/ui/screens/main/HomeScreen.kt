package com.aiinty.dragonfly.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.TopAppBarState
import com.aiinty.dragonfly.ui.TopAppBarStateProvider
import com.aiinty.dragonfly.ui.components.BaseHeader
import com.aiinty.dragonfly.ui.components.PocketSection
import com.aiinty.dragonfly.ui.theme.Gray
import com.aiinty.dragonfly.ui.theme.Outline
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import com.aiinty.dragonfly.ui.theme.Secondary
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Composable
fun HomeScreen() {
    TopAppBarStateProvider.update(
        TopAppBarState {
            HomeHeader()
        }
    )

    val cards = listOf(
        PocketCardItem(
            stringResource(R.string.saving_balance),
            R.drawable.cc1
        ),
        PocketCardItem(
            stringResource(R.string.family_balance),
            R.drawable.cc2
        ),
        PocketCardItem(
            stringResource(R.string.investment_balance),
            R.drawable.cc3
        ),
        PocketCardItem(
            stringResource(R.string.alms_balance),
            R.drawable.cc4
        ),
        PocketCardItem(
            stringResource(R.string.saving_balance),
            R.drawable.cc1
        ),
        PocketCardItem(
            stringResource(R.string.family_balance),
            R.drawable.cc2
        ),
        PocketCardItem(
            stringResource(R.string.investment_balance),
            R.drawable.cc3
        ),
        PocketCardItem(
            stringResource(R.string.alms_balance),
            R.drawable.cc4
        )
    )

    LazyColumn (
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(10.dp, 16.dp)
    ) {
        item {
            BalanceSection()
        }

        item {
            ActionButtons()
        }

        item {
            ConnectCard()
        }

        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.pocket), stringResource(R.string.my_pocket), tint = PrimaryContainer)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.my_pocket))
                    TextButton({}, contentPadding = PaddingValues(0.dp)) {
                        Text(stringResource(R.string.create_pocket), lineHeight = 18.sp, fontSize = 12.sp, color = PrimaryContainer)
                    }
                }
            }

            PocketSection(cards) {}
        }

        item { CurrencySection() }

        item { Spacer(Modifier.size(16.dp)) } // FIXME another way pls

    }
}

@Composable
fun BalanceSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(stringResource(R.string.your_balance), fontSize = 12.sp, lineHeight = 18.sp, color = Gray)

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween ) {
            Text(stringResource(R.string.user_money), fontSize = 24.sp, lineHeight = 30.sp, color = Secondary)
            Icon(ImageVector.vectorResource(R.drawable.eye), stringResource(R.string.hide_balance))
        }
    }
}

@Composable
fun ActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Outline, RoundedCornerShape(8.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ActionButton(stringResource(R.string.send_money)) {
            Icon(
                ImageVector.vectorResource(R.drawable.money_send), stringResource(R.string.send_money), Modifier.size(31.dp, 18.dp)
            )
        }

        ActionButton(stringResource(R.string.request_money)) {
            Icon(
                ImageVector.vectorResource(R.drawable.money_recive), stringResource(R.string.request_money), Modifier.size(31.dp, 18.dp)
            )
        }

        ActionButton(stringResource(R.string.balance_history)) {
            Icon(
                ImageVector.vectorResource(R.drawable.receipt), stringResource(R.string.balance_history), Modifier.size(31.dp, 18.dp)
            )
        }
    }
}

@Composable
fun ActionButton(title: String, icon: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable {}
    ) {
        icon()
        Text(title, fontSize = 12.sp, lineHeight = 18.sp)
    }
}

@Composable
fun ConnectCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Image(bitmap = ImageBitmap.imageResource(R.drawable.offer), modifier =  Modifier.fillMaxWidth(), contentDescription = stringResource(
            R.string.connect_offer
        ),
            contentScale = ContentScale.FillWidth
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(32.dp)) {
            Text(stringResource(R.string.connect_card), Modifier, Color(0xFF7443FF), style = MaterialTheme.typography.labelMedium, lineHeight = 30.sp)
            Text(stringResource(R.string.connect_card_desc), Modifier, Gray, style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp), lineHeight = 18.sp)
        }
    }
}

@Composable
fun CurrencySection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.coin), stringResource(R.string.currency), tint = PrimaryContainer)
            Text(text = stringResource(R.string.currency), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Outline, RoundedCornerShape(8.dp)),
        ) {
            // Our treasure
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                val currencies = listOf(
                    CurrencyItem("USD", "1.00", "1.00"),
                    CurrencyItem("EURO", "1.00", "0.92"),
                    CurrencyItem("POND", "1.00", "0.79"),
                    CurrencyItem("JPY", "1.00", "144.34")
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(R.string.currency), style = MaterialTheme.typography.labelSmall)

                        currencies.forEach { c ->
                            Text(c.name, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(R.string.price), style = MaterialTheme.typography.labelSmall)

                        currencies.forEach { c->
                            Text(c.rate, style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(R.string.rate), style = MaterialTheme.typography.labelSmall)

                        currencies.forEach { c ->
                            Text(c.price, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                Text(text = stringResource(R.string.pocket_updated), fontSize = 12.sp, color = PrimaryContainer)
            }
        }
    }
}

data class CurrencyItem(val name: String, val price: String, val rate: String)

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
) {
    BaseHeader(
        modifier = modifier.padding(8.dp),
        {
            Image(
                painter = painterResource(R.drawable.header_with_text),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(90.dp, 22.dp)
            )
        },
        {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.scanner),
                contentDescription = stringResource(R.string.scanner)
            )
        }
    )
}

fun NavController.navigateToHome(navOptions: NavOptions) =
    navigate(route = HomeRoute, navOptions)

fun NavGraphBuilder.homeScreen() {
    composable<HomeRoute> {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeHeaderPreview() {
    HomeHeader()
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    HomeScreen()
}
