package com.aiinty.dragonfly.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aiinty.dragonfly.R
import kotlinx.serialization.Serializable

@Serializable
object Loading

@Composable
fun LoadingScreen(onNavigateToNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(count = 1) {
                OnboardingFirst()
                OnboardingFirst()
                OnboardingFirst()
            }
        }

        //Text(text = "Swipe for more ")

        Button(
            onClick = { onNavigateToNext() },
            Modifier
                .fillMaxWidth()
                .height(53.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Get Started", fontSize = 14.sp, lineHeight = 21.sp)
        }
    }
}

@Composable
private fun OnboardingFirst() {

    //Image(bitmap = ImageBitmap.imageResource(R.drawable.credit_card), contentDescription = "credit card")

    Text(
        text = "Easy to manage money",
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = "Transfer and receive your money easily with dragonfly bank",
        style = MaterialTheme.typography.bodyMedium
    )
}