package com.awilab.moviedb.ui.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awilab.moviedb.ui.widgets.SearchFieldBar

@Composable
fun SearchPage(
    vm: SearchViewModel = hiltViewModel(),
) {
    val keyword by vm.query.collectAsStateWithLifecycle()
    val resultList by vm.searchResultList.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SearchFieldBar(
                query = keyword,
                onQueryChange = vm::onQueryChanged,
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(resultList) { item ->
                    SearchResultItem(item)
                }
            }
        }
    }
}