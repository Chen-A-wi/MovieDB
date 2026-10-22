package com.awilab.moviedb.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.awilab.moviedb.ui.detail.DetailPage
import com.awilab.moviedb.ui.detail.DetailViewModel
import com.awilab.moviedb.ui.home.HomePage
import com.awilab.moviedb.ui.search.SearchPage

@Composable
fun MovieDbNavDisplay(
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val entryProvider = entryProvider {
        homeEntries()
        searchEntries(navigator)
    }

    NavDisplay(
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.goBack() },
        modifier = modifier,
    )
}

private fun EntryProviderScope<NavKey>.homeEntries() {
    entry<HomeRoute> { HomePage() }
}

private fun EntryProviderScope<NavKey>.searchEntries(navigator: Navigator) {
    entry<SearchRoute> {
        SearchPage(onMovieClick = { movieId -> navigator.navigate(DetailRoute(movieId)) })
    }
    entry<DetailRoute> { key ->
        DetailPage(
            vm = hiltViewModel<DetailViewModel, DetailViewModel.Factory>(
                creationCallback = { factory -> factory.create(key) },
            ),
            onBack = navigator::goBack,
        )
    }
}
