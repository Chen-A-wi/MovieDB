package com.awilab.moviedb.common.navigation

sealed interface NavRoute {
    val route: String
}

sealed class NavGraph(override val route: String) : NavRoute {
    data object Home : NavGraph("home_graph")
    data object Search : NavGraph("search_graph")
}

sealed class NavScreen(override val route: String) : NavRoute {
    data object Home : NavScreen("home")

    data object Search : NavScreen("search") {
        const val ARG_RESULT = "arg_result"
    }

    data object Detail : NavScreen("detail")
}
