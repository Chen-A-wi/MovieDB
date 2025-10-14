package com.awilab.moviedb.common.navigation

sealed interface NavRoute {
    val route: String
}

sealed class NavGraph(override val route: String) : NavRoute {
    object Home : NavGraph("home_graph")
    object Search : NavGraph("search_graph")
}

sealed class NavScreen(override val route: String) : NavRoute {
    object Home : NavScreen("home")

    object Search : NavScreen("search") {
        const val ARG_RESULT = "arg_result"
    }

    object Detail : NavScreen("detail")
}
