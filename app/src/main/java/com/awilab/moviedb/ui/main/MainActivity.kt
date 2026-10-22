package com.awilab.moviedb.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.awilab.moviedb.common.navigation.HomeRoute
import com.awilab.moviedb.common.navigation.MovieDbNavDisplay
import com.awilab.moviedb.common.navigation.Navigator
import com.awilab.moviedb.common.navigation.rememberNavigationState
import com.awilab.moviedb.common.navigation.topLevelRoutes
import com.awilab.moviedb.ui.theme.MovieDBTheme
import com.awilab.moviedb.ui.widgets.BottomNavBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navigationState = rememberNavigationState(
                startRoute = HomeRoute,
                topLevelRoutes = topLevelRoutes,
            )
            val navigator = remember(navigationState) { Navigator(navigationState) }

            MovieDBTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = navigationState.currentRoute in topLevelRoutes,
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it })
                        ) {
                            BottomNavBar(
                                selectedRoute = navigationState.topLevelRoute,
                                onItemClick = navigator::navigate,
                            )
                        }
                    }
                ) { innerPadding ->
                    MovieDbNavDisplay(
                        navigationState = navigationState,
                        navigator = navigator,
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)  // 取消 NavDisplay 到內部會預留的 padding
                    )
                }
            }
        }
    }
}
