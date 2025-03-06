package com.awilab.moviedb.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.awilab.moviedb.common.navigation.MovieDbNavGraph
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
                    bottomBar = { BottomNavBar(navController = navController) }
                ) { innerPadding ->
                    MovieDbNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    Text(
        text = "Hello $name!",
        modifier = modifier.clickable { mainViewModel.test() }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieDBTheme {
        Greeting("Android")
    }
}