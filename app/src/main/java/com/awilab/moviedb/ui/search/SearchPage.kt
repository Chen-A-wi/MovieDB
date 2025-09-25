package com.awilab.moviedb.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awilab.moviedb.common.navigation.MovieDbDestination
import com.awilab.moviedb.ui.widgets.SearchFieldBar

@Composable
fun SearchPage(
    vm: SearchViewModel = hiltViewModel(),
) {
    val keyword by vm.query.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.search() // 測試打API
    }

    Scaffold(
        topBar = {
            SearchFieldBar(
                query = keyword,
                onQueryChange = vm::onQueryChanged,
                onSearch = vm::search,
                onClear = vm::clearQuery,
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(3),
//                modifier = Modifier.fillMaxSize(),
//            ) { }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Text(text = "Hello Search Screen", modifier = Modifier.clickable {
                    vm.navigator.navigate(MovieDbDestination.DetailDestination.route)
                })
            }
        }
    }
}