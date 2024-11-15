package com.aiinty.dragonfly.ui.screens.welcome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.TopAppBarState
import com.aiinty.dragonfly.ui.TopAppBarStateProvider
import com.aiinty.dragonfly.ui.components.BaseDivider
import com.aiinty.dragonfly.ui.components.BaseHeader
import com.aiinty.dragonfly.ui.theme.LightGray
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import kotlinx.serialization.Serializable

@Serializable
object InformationRoute

private data class item(
    val title: String = "",
    val desc: String = ""
)

@Composable
fun InformationScreen(
    onBackClick: () -> Unit
) {
    val items = listOf(
        item(
            stringResource(R.string.information_first_title),
            stringResource(R.string.information_first_desc),
        ),
        item(
            stringResource(R.string.information_second_title),
            stringResource(R.string.information_second_desc),
        )
    )

    LaunchedEffect(Unit) {
        TopAppBarStateProvider.update(
            TopAppBarState {
                InformationHeader(
                    onBackClick = onBackClick
                )
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = stringResource(R.string.information_title),
                style = MaterialTheme.typography.labelLarge,
                fontSize = 20.sp,
                lineHeight = 30.sp
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(items) { _, it ->
                    InformationItem(
                        title = it.title,
                        desc = it.desc,
                        onClick = { }
                    )
                }
            }
        }
    }
}

@Composable
fun InformationItem(
    title: String = "",
    desc: String = "",
    @DrawableRes iconId: Int = R.drawable.book,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(iconId),
                contentDescription = stringResource(R.string.information),
                tint = PrimaryContainer
            )
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.labelSmall
            )
        }
        Text(
            text = desc,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            color = LightGray
        )
        BaseDivider()
    }
}

@Composable
private fun InformationHeader(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    BaseHeader(
        modifier = modifier,
        {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                    contentDescription = stringResource(R.string.arrow)
                )
            }
        }, {
            Text(
                text = stringResource(R.string.information),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.W600
            )
        }, {
            Box(Modifier.size(24.dp))
        }
    )
}

fun NavController.navigateToInformation(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = InformationRoute, navOptions)

fun NavGraphBuilder.informationScreen(
    onPreviousNavigate: () -> Unit,
) {
    composable<InformationRoute> {
        InformationScreen(
            onBackClick = onPreviousNavigate
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InformationPreview() {
    InformationScreen {}
}

@Preview(showBackground = true)
@Composable
private fun InformationHeaderPreview() {
    InformationHeader {}
}


@Preview(showBackground = true)
@Composable
private fun InformationItemPreview() {
    InformationItem(
        title = "Title",
        desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer a ex libero.",
        onClick = {}
    )
}
