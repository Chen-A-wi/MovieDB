package com.awilab.moviedb.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MovieDbNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MovieDbDestination.HomeDestination.route,
        modifier = modifier,
    ) {
        bottomNavGraph(navController)
        searchGraph(navController)
    }
}
