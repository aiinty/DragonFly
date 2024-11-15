package com.aiinty.dragonfly.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.theme.ECECEC
import com.aiinty.dragonfly.ui.theme.PrimaryContainer

@Composable
fun PocketSection(items: List<@Composable () -> Unit>, viewMore: Boolean = true, onViewMoreClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.pocket), stringResource(R.string.my_pocket), tint = PrimaryContainer)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.my_pocket))
                TextButton({}, contentPadding = PaddingValues(0.dp)) {
                    Text(stringResource(R.string.create_pocket), lineHeight = 18.sp, fontSize = 12.sp, color = PrimaryContainer)
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

    if (viewMore) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextButton({ onViewMoreClick() },
                modifier = Modifier
                    .padding(vertical = 8.dp)) {

                Text(
                    stringResource(R.string.view_more),
                    color = Color.Blue)
            }
        }
    }
}

@Composable
fun PocketCard(title: String, @DrawableRes iconId: Int) {
    Column(Modifier.border(1.dp, ECECEC, RoundedCornerShape(8.dp))) {
        Image(
            ImageBitmap.imageResource(iconId), stringResource(R.string.card), Modifier
            .size(164.dp, 150.dp)
            .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillWidth)

        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(title, fontSize = 10.sp, lineHeight = 15.sp)
            Text(stringResource(R.string.pocket_cost), fontSize = 16.sp, color = PrimaryContainer, style = MaterialTheme.typography.labelMedium)
        }
    }
}