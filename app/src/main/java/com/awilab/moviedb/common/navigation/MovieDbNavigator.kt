package com.awilab.moviedb.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
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
            popUpTo(route) {
                saveState = true
                inclusive = true
            }
        }
    }

    fun <T> navigateWithBundle(bundleKey: String, data: T?, route: String) {
        navController.run {
            currentBackStackEntry?.savedStateHandle?.set(bundleKey, data)
            navigate(route)
        }
    }

    fun <T> getNavBundle(bundleKey: String): T? {
        return navController.previousBackStackEntry?.savedStateHandle?.get(bundleKey)
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
        launchSingleTop = true
        restoreState = true
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
    }
}