package com.awilab.moviedb.common.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.awilab.moviedb.R

data class BottomNavItem(
    val route: NavKey,
    val icon: ImageVector,
    @param:StringRes val title: Int,
)

val bottomNavItems = listOf(
    BottomNavItem(route = HomeRoute, icon = Icons.Filled.Home, title = R.string.lab_home),
    BottomNavItem(route = SearchRoute, icon = Icons.Filled.Search, title = R.string.lab_search),
)

val topLevelRoutes: Set<NavKey> = bottomNavItems.map { it.route }.toSet()
