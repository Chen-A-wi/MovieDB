package com.awilab.moviedb.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.awilab.moviedb.ui.detail.DetailPage
import com.awilab.moviedb.ui.home.HomePage
import com.awilab.moviedb.ui.search.SearchPage

fun NavGraphBuilder.bottomNavGraph(navController: NavHostController) {
    composable(MovieDbDestination.HomeDestination.route) {
        HomePage()
    }
}

fun NavGraphBuilder.searchGraph(navController: NavHostController) {
    navigation(startDestination = MovieDbDestination.SearchDestination.route, route = "search") {
        composable(MovieDbDestination.SearchDestination.route) { SearchPage(navController = navController) }
        composable(MovieDbDestination.DetailDestination.route) { DetailPage(navController = navController) }
    }
}