package com.awilab.moviedb.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.awilab.moviedb.common.navigation.MovieDbDestination
import com.awilab.moviedb.common.navigation.MovieDbNavHost
import com.awilab.moviedb.ui.theme.MovieDBTheme
import com.awilab.moviedb.ui.widgets.navigationbar.BottomNavBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            MovieDBTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {

                        val currentRoute = currentRoute(navController)
                        if (currentRoute in listOf(
                                MovieDbDestination.HomeDestination.route,
                                MovieDbDestination.SearchDestination.route
                            )
                        ) {
                            BottomNavBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    MovieDbNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
