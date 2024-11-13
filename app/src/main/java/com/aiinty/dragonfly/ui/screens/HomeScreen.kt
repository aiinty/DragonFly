package com.aiinty.dragonfly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.components.BaseHeader
import com.aiinty.dragonfly.ui.theme.Tertiary

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        BaseHeader({
            Image(
                painterResource(R.drawable.header_with_text),
                "dragonfly",
                Modifier.size(90.dp, 22.dp)
            )
        }, { Icon(ImageVector.vectorResource(R.drawable.scanner), "scanner") })

        BalanceSection()
        ActionButtons()
        ConnectCard()
        CurrencySection()
    }
}

@Composable
fun BalanceSection() {
    Text("Your Balance", fontSize = 16.sp, color = Color.Gray)

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("$49,250.00", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Icon(ImageVector.vectorResource(R.drawable.eye), "hide balance")
    }
}

@Composable
fun ActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6))
            .border(1.dp, Tertiary, RoundedCornerShape(6))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ActionButton("Send") {
            Icon(
                ImageVector.vectorResource(R.drawable.money_send), "send money"
            )
        }

        ActionButton("Request") {
            Icon(
                ImageVector.vectorResource(R.drawable.money_recive), "receive money"
            )
        }

        ActionButton("History") {
            Icon(
                ImageVector.vectorResource(R.drawable.receipt), "history"
            )
        }
    }
}

@Composable
fun ActionButton(title: String, icon: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        icon()
        Text(title)
    }
}

@Composable
fun ConnectCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Let's connect", fontWeight = FontWeight.Bold)
            Text("Connect account with marketplace for easy transactions and get $25 bonus.")
        }
    }
}

@Composable
fun PocketSection(items: List<String>, onViewMoreClick: () -> Unit) {

    Text("View more",
        color = Color.Blue,
        modifier = Modifier
            .clickable { onViewMoreClick() }
            .padding(vertical = 8.dp))
}

@Composable
fun PocketCard(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFEFEFFF))
    ) {
        Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.Center) {
            Text(title)
            Text("Balance: $1,000.00", fontSize = 12.sp) // TODO
        }
    }
}

@Composable
fun CurrencySection() {
    Column {
        Text(text = "Currency", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val currencies = listOf(
                    CurrencyItem("USD", "1.00", "1.00"),
                    CurrencyItem("EURO", "1.00", "0.92"),
                    CurrencyItem("POND", "1.00", "0.79"),
                    CurrencyItem("JPY", "1.00", "144.34")
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) { Text("Currency"); Text("Price"); Text("Rates") }

                currencies.forEach { currency ->
                    CurrencyRow(currency) // TODO row -> 3 columns lol
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(text = "Updated 1 hour ago", fontSize = 12.sp, color = Color.Gray)
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