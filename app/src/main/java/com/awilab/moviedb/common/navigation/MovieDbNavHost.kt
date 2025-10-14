package com.awilab.moviedb.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost

@Composable
fun MovieDbNavHost(
    navigator: MovieDbNavigator,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = NavScreen.Home.route,
        modifier = modifier,
    ) {
        homeGraph()
        searchGraph()
    }
}
