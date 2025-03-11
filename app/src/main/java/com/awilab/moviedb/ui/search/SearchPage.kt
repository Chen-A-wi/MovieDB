package com.awilab.moviedb.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.awilab.moviedb.R
import com.awilab.moviedb.common.navigation.MovieDbDestination
import com.awilab.moviedb.ui.widgets.AppBar

@Composable
fun SearchPage(
    vm: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            AppBar(titleRes = R.string.app_name)
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .clickable {
                },
        ) {
            Text(text = "Hello Search Screen", modifier = Modifier.clickable {
                navController.navigate(MovieDbDestination.DetailDestination.route)
            })
        }
    }
}