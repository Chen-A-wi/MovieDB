package com.awilab.moviedb.common.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.awilab.moviedb.R

val bottomNavPages = listOf(
    MovieDbDestination.HomeDestination,
    MovieDbDestination.SearchDestination
)

sealed class MovieDbDestination(
    val icon: ImageVector? = null,
    @StringRes val title: Int,
    val route: String,
) {
    data object HomeDestination : MovieDbDestination(
        icon = Icons.Filled.Home,
        title = R.string.lab_home,
        route = "home_main",
    )

    data object SearchDestination : MovieDbDestination(
        icon = Icons.Filled.Search,
        title = R.string.lab_search,
        route = "search_main",
    )

    data object DetailDestination : MovieDbDestination(
        title = R.string.lab_detail,
        route = "detail_main",
    )
}
