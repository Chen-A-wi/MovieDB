package com.awilab.moviedb.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import com.awilab.moviedb.common.navigation.BottomNavItem
import com.awilab.moviedb.common.navigation.bottomNavItems

@Composable
fun BottomNavBar(
    selectedRoute: NavKey,
    onItemClick: (NavKey) -> Unit,
    bottomNavList: List<BottomNavItem> = bottomNavItems,
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        bottomNavList.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route,
                onClick = { onItemClick(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
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