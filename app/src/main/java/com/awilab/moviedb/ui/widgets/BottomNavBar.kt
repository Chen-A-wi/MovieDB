package com.awilab.moviedb.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.awilab.moviedb.common.navigation.MovieDbDestination
import com.awilab.moviedb.common.navigation.MovieDbNavigator
import com.awilab.moviedb.common.navigation.bottomNavPages

@Composable
fun BottomNavBar(
    navigator: MovieDbNavigator,
    bottomNavList: List<MovieDbDestination> = bottomNavPages,
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        bottomNavList.forEach { item ->
            NavigationBarItem(
                selected = navigator.getCurrentRoute() == item.route,
                onClick = { navigator.navigateSingleTopTo(item.route) },
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