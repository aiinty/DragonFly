package com.aiinty.dragonfly.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.screens.main.PocketCardItem
import com.aiinty.dragonfly.ui.theme.Outline
import com.aiinty.dragonfly.ui.theme.PrimaryContainer

@Composable
fun PocketSection(items: List<PocketCardItem>, viewMore: Boolean = true, onViewMoreClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items.chunked(2).forEach {  rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                for (item in rowItems) {
                    PocketCard(
                        modifier = Modifier.weight(1f),
                        title = item.title,
                        iconId = item.iconId
                    )
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }

    if (viewMore) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TextButton({ onViewMoreClick() },
                modifier = Modifier
                    .padding(vertical = 8.dp)) {

                Text(
                    stringResource(R.string.view_more),
                    color = PrimaryContainer)
            }
        }
    }
}

@Composable
fun PocketCard(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes iconId: Int
) {
    Column(
        modifier.border(1.dp, Outline, RoundedCornerShape(8.dp))) {
        Image(
            ImageBitmap.imageResource(iconId), stringResource(R.string.card),
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillWidth)

        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(title, fontSize = 10.sp, lineHeight = 15.sp)
            Text(stringResource(R.string.pocket_cost), fontSize = 16.sp, color = PrimaryContainer, style = MaterialTheme.typography.labelMedium)
        }
    }
}
