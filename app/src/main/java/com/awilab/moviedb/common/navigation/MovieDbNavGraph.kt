package com.awilab.moviedb.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.awilab.moviedb.ui.detail.DetailPage
import com.awilab.moviedb.ui.home.HomePage
import com.awilab.moviedb.ui.search.SearchPage

fun NavGraphBuilder.homeGraph() {
    composable(NavScreen.Home.route) {
        HomePage()
    }
}

fun NavGraphBuilder.searchGraph() {
    navigation(startDestination = NavScreen.Search.route, route = NavGraph.Search.route) {
        composable(NavScreen.Search.route) { SearchPage() }
        composable(NavScreen.Detail.route) { DetailPage() }
    }
}