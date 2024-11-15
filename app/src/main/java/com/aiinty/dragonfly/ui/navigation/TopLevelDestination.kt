package com.aiinty.dragonfly.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.ui.screens.main.HomeRoute
import com.aiinty.dragonfly.ui.screens.main.InboxRoute
import com.aiinty.dragonfly.ui.screens.main.PocketRoute
import com.aiinty.dragonfly.ui.screens.main.profile.ProfileRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    @DrawableRes val selectedIconId: Int,
    @DrawableRes val unselectedIconId: Int = selectedIconId,
    @StringRes val titleTextId: Int,
    @StringRes val iconTextId: Int = titleTextId,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    HOME(
        selectedIconId = R.drawable.home,
        titleTextId = R.string.home,
        route = HomeRoute::class,
    ),
    POCKET(
        selectedIconId = R.drawable.wallet,
        titleTextId = R.string.pocket,
        route = PocketRoute::class,
    ),
    INBOX(
        selectedIconId = R.drawable.inbox,
        titleTextId = R.string.inbox,
        route = InboxRoute::class,
    ),
    PROFILE(
        selectedIconId = R.drawable.profile,
        titleTextId = R.string.profile,
        route = ProfileRoute::class,
    )
}