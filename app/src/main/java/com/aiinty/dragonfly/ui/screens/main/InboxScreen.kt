package com.aiinty.dragonfly.ui.screens.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.TopAppBarState
import com.aiinty.dragonfly.ui.TopAppBarStateProvider
import com.aiinty.dragonfly.ui.components.BaseDivider
import com.aiinty.dragonfly.ui.components.BaseHeader
import com.aiinty.dragonfly.ui.components.BaseTab
import com.aiinty.dragonfly.ui.components.BaseTabRow
import com.aiinty.dragonfly.ui.theme.Gray
import com.aiinty.dragonfly.ui.theme.LightGray
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import com.aiinty.dragonfly.ui.theme.Tertiary
import kotlinx.serialization.Serializable

@Serializable
object InboxRoute

@Composable
fun InboxScreen(
    selectedTabIdx: Int = 0
) {
    var tabIdx by remember { mutableStateOf(selectedTabIdx) }
    val tabsTitles = arrayOf(
        stringResource(R.string.inbox),
        stringResource(R.string.inbox_notification)
    )
    val inboxItems = listOf(
        InboxItem(
            stringResource(R.string.inbox_item_first_title),
            stringResource(R.string.inbox_item_first_desc),
            true
        ),
        InboxItem(
            stringResource(R.string.inbox_item_second_title),
            stringResource(R.string.inbox_item_second_desc),
            false
        ),
    )
    val notificationItems = listOf(
        NotificationItem(
            stringResource(R.string.inbox_notification_first_title),
            stringResource(R.string.inbox_notification_first_desc),
            R.drawable.notification_pic1,
            true
        ),
        NotificationItem(
            stringResource(R.string.inbox_notification_second_title),
            stringResource(R.string.inbox_notification_second_desc),
            R.drawable.notification_pic2,
            false
        ),
    )

    LaunchedEffect(Unit) {
        TopAppBarStateProvider.update(
            TopAppBarState {
                InboxHeader()
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        BaseTabRow(
            selectedTabIndex = tabIdx
        ) {
            tabsTitles.forEachIndexed() { idx, title ->
                BaseTab(
                    selected = tabIdx == idx,
                    onClick = { tabIdx = idx },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
            }
        }
        when (tabIdx) {
            0 -> InboxList(inboxItems)
            1 -> NotificationList(notificationItems)
        }
    }
}

private data class InboxItem(
    val title: String,
    val desc: String,
    val isNew: Boolean = true,
)

@Composable
private fun InboxList(
    inboxItems: List<InboxItem>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(inboxItems) { _, it ->
            InboxListItem(
                title = it.title,
                desc = it.desc,
                isNew = it.isNew
            )
        }
    }
}

@Composable
private fun InboxListItem(
    title : String,
    desc: String,
    isNew: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryContainer.copy(alpha = 0.05f))
            ) {
                Icon(
                    modifier = Modifier
                        .padding(12.dp, 9.dp)
                        .size(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.notification),
                    contentDescription = stringResource(R.string.inbox_notification),
                    tint = PrimaryContainer
                )
            }
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isNew) PrimaryContainer else Gray
                )

                Text(
                    text = desc,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = if (isNew) Tertiary else LightGray
                )
            }
        }
        BaseDivider()
    }
}

@Composable
private fun NotificationList(
    notificationItems: List<NotificationItem>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        itemsIndexed(notificationItems) { _, it ->
            NotificationListItem(
                title = it.title,
                desc = it.desc,
                picId = it.picId,
                isNew = it.isNew
            )
        }
    }
}

private data class NotificationItem(
    val title: String,
    val desc: String,
    @DrawableRes val picId: Int,
    val isNew: Boolean = true
)

@Composable
private fun NotificationListItem(
    title : String,
    desc: String,
    @DrawableRes picId: Int,
    isNew: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = if (isNew) PrimaryContainer else Gray
        )

        Text(
            text = desc,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = if (isNew) Tertiary else LightGray
        )

        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxSize(),
            bitmap = ImageBitmap.imageResource(picId),
            contentDescription = title,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
private fun InboxHeader(
    modifier: Modifier = Modifier
) {
    BaseHeader(
        modifier = modifier.padding(8.dp),
        {
            Text(
                text = stringResource(R.string.inbox),
                style = MaterialTheme.typography.labelSmall
            )
        }
    )
}

fun NavController.navigateToInbox(navOptions: NavOptions) =
    navigate(route = InboxRoute, navOptions)

fun NavGraphBuilder.inboxScreen() {
    composable<InboxRoute> {
        InboxScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun InboxHeaderPreview() {
    InboxHeader()
}

@Preview(showBackground = true)
@Composable
private fun InboxPreview() {
    InboxScreen(0)
}

@Preview(showBackground = true)
@Composable
private fun InboxNotificationPreview() {
    InboxScreen(1)
}
