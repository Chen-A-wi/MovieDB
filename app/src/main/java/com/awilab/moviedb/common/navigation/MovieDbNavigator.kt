package com.awilab.moviedb.common.navigation

import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class MovieDbNavigator @Inject constructor() {
    var navController: NavController by Delegates.notNull()

    // 導航到指定路由
    fun navigate(route: String) {
        navController.navigate(route)
    }

    // 返回上一頁
    fun goBack() {
        navController.popBackStack()
    }
}