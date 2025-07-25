package com.awilab.moviedb.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awilab.moviedb.R
import com.awilab.moviedb.common.navigation.MovieDbDestination
import com.awilab.moviedb.ui.widgets.AppBar
import com.awilab.moviedb.ui.widgets.SearchFieldBar

@Composable
fun SearchPage(
    vm: SearchViewModel = hiltViewModel(),
) {
    val keyword by vm.keyword.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SearchFieldBar(
                query = keyword,
                onQueryChange = vm::updateKeyword,
                onSearch = vm::search,
                onClear = vm::clearKeyword,
                hint = {
                    Text(text = "電影名、演員等．．．")
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .clickable {
                    },
            ) {
                Text(text = "Hello Search Screen", modifier = Modifier.clickable {
                    vm.navigator.navigate(MovieDbDestination.DetailDestination.route)
                })
            }
        }
    }
}