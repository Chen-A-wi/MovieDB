package com.awilab.moviedb.common.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.awilab.moviedb.R

val bottomNavPages = listOf(MovieDbDestination.Home, MovieDbDestination.Search)

sealed class MovieDbDestination(
    val icon: ImageVector,
    @StringRes val title: Int,
    val route: String,
) {
    data object Home : MovieDbDestination(
        icon = Icons.Filled.Home,
        title = R.string.lab_home,
        route = "home",
    )

    data object Search : MovieDbDestination(
        icon = Icons.Filled.Search,
        title = R.string.lab_search,
        route = "search",
    )
}
