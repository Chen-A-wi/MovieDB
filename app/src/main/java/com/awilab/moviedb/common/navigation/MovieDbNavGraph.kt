package com.awilab.moviedb.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.awilab.moviedb.ui.detail.DetailPage
import com.awilab.moviedb.ui.home.HomePage
import com.awilab.moviedb.ui.search.SearchPage

fun NavGraphBuilder.bottomNavGraph() {
    composable(MovieDbDestination.HomeDestination.route) {
        HomePage()
    }
}

fun NavGraphBuilder.searchGraph() {
    navigation(startDestination = MovieDbDestination.SearchDestination.route, route = "search") {
        composable(MovieDbDestination.SearchDestination.route) { SearchPage() }
        composable(MovieDbDestination.DetailDestination.route) { DetailPage() }
    }
}