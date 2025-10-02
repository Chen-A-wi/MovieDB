package com.awilab.moviedb.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class MovieDbNavigator @Inject constructor() {
    var navController: NavHostController by Delegates.notNull()

    // 導航到指定路由
    fun navigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    // 返回上一頁
    fun goBack() {
        navController.popBackStack()
    }

    @Composable
    fun getCurrentRoute(): String? {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        return navBackStackEntry?.destination?.route
    }

    fun navigateSingleTopTo(route: String) = navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}