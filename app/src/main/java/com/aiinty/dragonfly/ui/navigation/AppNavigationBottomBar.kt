package com.aiinty.dragonfly.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.aiinty.dragonfly.ui.AppState
import com.aiinty.dragonfly.ui.rememberAppState
import com.aiinty.dragonfly.ui.theme.LightGray
import com.aiinty.dragonfly.ui.theme.PrimaryContainer

@Composable
fun AppNavigationBottomBar(
    modifier: Modifier = Modifier,
    appState: AppState,
) {
    NavigationBar (
        modifier = modifier,
    ) {
        TopLevelDestination.entries.forEach {
            AppNavigationBarItem(
                selected = appState.currentTopLevelDestination == it,
                selectedIcon = {
                    Icon(
                        ImageVector.vectorResource(it.selectedIconId),
                        stringResource(id = it.iconTextId),
                        tint = PrimaryContainer
                    )
                },
                icon = {
                    Icon(
                        ImageVector.vectorResource(it.unselectedIconId),
                        stringResource(id = it.iconTextId),
                        tint = LightGray
                    )
                },
                label = {

                    Text(
                        text = stringResource(it.titleTextId),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (appState.currentTopLevelDestination == it) PrimaryContainer else LightGray,
                        lineHeight = 18.sp
                    )
                },
                onClick = { appState.navigateToTopLevelDestination(it) }
            )
        }
    }
}

@Composable
private fun RowScope.AppNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
){
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppNavigationBottomBar(
        Modifier,
        rememberAppState()
    )
}