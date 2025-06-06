package com.awilab.moviedb.common.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.ActivityNavigator
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
        navController.navigate(route)
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

    // 跳 Activity
    fun launchActivity(context: Context, activity: Class<*>) {
        val intent = Intent(context, activity)
        context.startActivity(intent)
    }

    fun navigateSingleTopTo(route: String) = navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}