package com.aiinty.dragonfly.ui.components

import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aiinty.dragonfly.ui.theme.PrimaryContainer


@Composable
fun BaseTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        if (selectedTabIndex < tabPositions.size) {
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = PrimaryContainer
            )
        }
    },
    divider: @Composable () -> Unit = @Composable {
        BaseDivider()
    },
    tabs: @Composable () -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        indicator = indicator,
        divider = divider,
        tabs = tabs,
    )
}