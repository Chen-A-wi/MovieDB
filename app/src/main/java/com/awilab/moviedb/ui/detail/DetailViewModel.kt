package com.awilab.moviedb.ui.detail

import androidx.lifecycle.ViewModel
import com.awilab.data.local.search.SearchToDetailArgs
import com.awilab.moviedb.common.navigation.MovieDbNavigator
import com.awilab.moviedb.common.navigation.NavScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val navigator: MovieDbNavigator
) : ViewModel() {
    private val _movieId = MutableStateFlow(0)
    val movieId: StateFlow<Int> = _movieId.asStateFlow()

    fun initArgs() {
        navigator.getNavBundle<SearchToDetailArgs>(NavScreen.Search.ARG_RESULT)?.run {
            _movieId.update { this.movieId }
        }
    }
}