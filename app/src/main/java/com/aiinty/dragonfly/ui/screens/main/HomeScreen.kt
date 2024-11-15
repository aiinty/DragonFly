package com.aiinty.dragonfly.ui.screens.main

import androidx.annotation.DrawableRes
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
import com.aiinty.dragonfly.ui.theme.ECECEC
import com.aiinty.dragonfly.ui.theme.Gray
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

    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(Modifier.size(16.dp)) }
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
            PocketSection(
                listOf(
                    {
                        PocketCard(
                            "lol",
                            R.drawable.cc1
                        )
                    },
                    {
                        PocketCard(
                            "lol",
                            R.drawable.cc1
                        )
                    },
                    {
                        PocketCard(
                            "lol",
                            R.drawable.cc1
                        )
                    },
                )
            ) {
            }
        }

        item { CurrencySection() }
    }
}

@Composable
fun BalanceSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Your Balance", fontSize = 12.sp, lineHeight = 18.sp, color = Gray)

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween ) {
            Text("$ 49,250.00", fontSize = 24.sp, lineHeight = 30.sp, color = Secondary)
            Icon(ImageVector.vectorResource(R.drawable.eye), "hide balance")
        }
    }
}

@Composable
fun ActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, ECECEC, RoundedCornerShape(8.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ActionButton("Send") {
            Icon(
                ImageVector.vectorResource(R.drawable.money_send), "send money", Modifier.size(31.dp, 18.dp)
            )
        }

        ActionButton("Request") {
            Icon(
                ImageVector.vectorResource(R.drawable.money_recive), "receive money", Modifier.size(31.dp, 18.dp)
            )
        }

        ActionButton("History") {
            Icon(
                ImageVector.vectorResource(R.drawable.receipt), "history", Modifier.size(31.dp, 18.dp)
            )
        }
    }
}

@Composable
fun ActionButton(title: String, icon: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.clip(RoundedCornerShape(4.dp)).clickable {}
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
        Image(bitmap = ImageBitmap.imageResource(R.drawable.offer), modifier =  Modifier.fillMaxWidth(), contentDescription = "connect offer",
            contentScale = ContentScale.FillWidth
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(32.dp)) {
            Text("Let's connect", Modifier, Color(0xFF7443FF), style = MaterialTheme.typography.labelMedium, lineHeight = 30.sp)
            Text("Connect account with marketplace for easy transactions and get \$25 bonus.", Modifier, Gray, style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp), lineHeight = 18.sp)
        }
    }
}

@Composable
fun PocketSection(items: List<@Composable () -> Unit>, onViewMoreClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.pocket), "my pocket", tint = PrimaryContainer)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("My Pocket")
                TextButton({}, contentPadding = PaddingValues(0.dp)) {
                    Text("Create", lineHeight = 18.sp, fontSize = 12.sp, color = PrimaryContainer)
                }
            }
        }

        items.chunked(2).forEach {  rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                for (item in rowItems) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        item()
                    }
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton({ onViewMoreClick() },
            modifier = Modifier
                .padding(vertical = 8.dp)) {

            Text(
                "View more",
                color = Color.Blue)
        }
    }
}

@Composable
fun PocketCard(title: String, @DrawableRes iconId: Int) {
    Column(Modifier.border(1.dp, ECECEC, RoundedCornerShape(8.dp))) {
            Image(ImageBitmap.imageResource(iconId), "card", Modifier
                .size(164.dp, 150.dp)
                .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillWidth)

        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(title, fontSize = 10.sp, lineHeight = 15.sp)
            Text("$1,000.00", fontSize = 16.sp, color = PrimaryContainer, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun CurrencySection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.coin), "currencies", tint = PrimaryContainer)
            Text(text = "Currency", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, ECECEC, RoundedCornerShape(8.dp)),
        ) {
            // Our treasure
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                val currencies = listOf(
                    CurrencyItem("USD", "1.00", "1.00"),
                    CurrencyItem("EURO", "1.00", "0.92"),
                    CurrencyItem("POND", "1.00", "0.79"),
                    CurrencyItem("JPY", "1.00", "144.34")
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Currency", style = MaterialTheme.typography.labelSmall)

                        currencies.forEach { c ->
                            Text(c.name, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Price", style = MaterialTheme.typography.labelSmall)

                        currencies.forEach { c->
                            Text(c.rate, style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Rate", style = MaterialTheme.typography.labelSmall)

                        currencies.forEach { c ->
                            Text(c.price, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                Text(text = "Updated 1 hour ago", fontSize = 12.sp, color = PrimaryContainer)
            }
        }
    }
}

data class CurrencyItem(val name: String, val price: String, val rate: String)

@Composable
fun CurrencyRow(item: CurrencyItem) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name, fontSize = 16.sp)
        Text(text = item.price, fontSize = 16.sp)
        Text(text = item.rate, fontSize = 16.sp)
    }
}

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
                contentDescription = "scanner"
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
