package com.awilab.moviedb.ui.search

import androidx.lifecycle.ViewModel
import com.awilab.moviedb.common.navigation.MovieDbNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val navigator: MovieDbNavigator
) : ViewModel() {
    private val _keyword = MutableStateFlow("")
    val keyword: StateFlow<String> = _keyword.asStateFlow()

    fun updateKeyword(query: String) {
        _keyword.update { query }
    }

    fun search() {
        TODO("Not yet implemented")
    }

    fun clearKeyword() {
        _keyword.update { "" }
    }
}