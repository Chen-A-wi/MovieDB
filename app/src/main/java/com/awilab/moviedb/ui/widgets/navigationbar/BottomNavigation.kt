package com.awilab.moviedb.ui.widgets.navigationbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.awilab.common.extension.navigateSingleTopTo
import com.awilab.moviedb.common.navigation.MovieDbDestination
import com.awilab.moviedb.common.navigation.bottomNavPages

@Composable
fun BottomNavBar(
    navController: NavHostController,
    bottomNavList: List<MovieDbDestination> = bottomNavPages,
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        bottomNavList.forEach { item ->
            NavigationBarItem(
                selected = currentDestination == item.route,
                onClick = { navController.navigateSingleTopTo(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon ?: Icons.Filled.Home,
                        contentDescription = stringResource(id = item.title),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.title)
                    )
                },
            )
        }
    }
}