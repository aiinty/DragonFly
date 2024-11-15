package com.aiinty.dragonfly.ui.screens.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.TopAppBarState
import com.aiinty.dragonfly.ui.TopAppBarStateProvider
import com.aiinty.dragonfly.ui.components.BaseHeader
import com.aiinty.dragonfly.ui.components.PocketSection
import com.aiinty.dragonfly.ui.theme.Outline
import com.aiinty.dragonfly.ui.theme.PrimaryContainer
import com.aiinty.dragonfly.ui.theme.Secondary
import kotlinx.serialization.Serializable

@Serializable
object PocketRoute

@Composable
fun PocketScreen() {
    TopAppBarStateProvider.update(
        TopAppBarState {
            PocketHeader()
        }
    )
    val chipsTitles = listOf(
        "All",
        "Saving",
        "Family",
        "Investment",
        "Alms",
        "Another other"
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

    Column(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ToggleablePocketChips(chipsTitles)

        PocketSection(cards, viewMore = false) { }
    }
}

data class PocketCardItem(val title: String, @DrawableRes val iconId: Int)

@Composable
fun ToggleablePocketChips(items: List<String>) {
    var selectedCategory by remember { mutableStateOf("") }

    Row(verticalAlignment = Alignment.CenterVertically) {
        LazyRow(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(items) { _, it ->
                Chips(it, selectedCategory == it) { category ->
                    selectedCategory = category
                }
            }
        }

        IconButton({}) {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.add), "add")
        }
    }
}

@Composable
fun Chips(name: String, isSelected: Boolean, onSelectChanged: (String) -> Unit) {
    Surface(
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, if (isSelected) PrimaryContainer else Outline, RoundedCornerShape(8.dp))
            .clickable {
                onSelectChanged(name)
            }) {
        Row(Modifier.padding(vertical = 4.dp, horizontal = 10.dp)) {
            Text(name, color = if (isSelected) PrimaryContainer else Secondary)
        }
    }
}

@Composable
private fun PocketHeader(
    modifier: Modifier = Modifier
) {
    BaseHeader(
        modifier = modifier.padding(8.dp),
        {
            Text(
                text = stringResource(R.string.pocket),
                style = MaterialTheme.typography.labelMedium
            )
        }
    )
}

fun NavController.navigateToPocket(navOptions: NavOptions) =
    navigate(route = PocketRoute, navOptions)

fun NavGraphBuilder.pocketScreen() {
    composable<PocketRoute> {
        PocketScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun PocketPreview() {
    PocketScreen()
}