package com.awilab.moviedb.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.awilab.moviedb.common.navigation.MovieDbNavHost
import com.awilab.moviedb.common.navigation.MovieDbNavigator
import com.awilab.moviedb.common.navigation.bottomNavPages
import com.awilab.moviedb.ui.theme.MovieDBTheme
import com.awilab.moviedb.ui.widgets.BottomNavBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: MovieDbNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            navigator.navController = navController

            MovieDBTheme {
                Scaffold(modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = bottomNavPages.any { it.route == navigator.getCurrentRoute() },
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it })
                        ) {
                            BottomNavBar(navigator = navigator)
                        }
                    }
                ) { innerPadding ->
                    MovieDbNavHost(
                        navigator = navigator,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
