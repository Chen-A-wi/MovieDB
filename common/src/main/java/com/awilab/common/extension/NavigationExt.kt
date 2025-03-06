package com.awilab.common.extension

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    popUpTo(this@navigateSingleTopTo.graph.startDestinationId) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}