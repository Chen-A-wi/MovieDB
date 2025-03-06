package com.awilab.moviedb.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.awilab.moviedb.ui.home.HomePage
import com.awilab.moviedb.ui.search.SearchPage

@Composable
fun MovieDbNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MovieDbDestination.Home.route,
        modifier = modifier,
    ) {
        composable(MovieDbDestination.Home.route) {
            HomePage()
        }
        composable(MovieDbDestination.Search.route) {
            SearchPage()
        }
    }
}
