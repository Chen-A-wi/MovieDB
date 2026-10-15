package com.awilab.moviedb.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awilab.common.coroutine.CommonDispatcherProvider
import com.awilab.domain.model.Movie
import com.awilab.domain.model.Resource
import com.awilab.domain.usecase.SearchMoviesUseCase
import com.awilab.moviedb.common.navigation.MovieDbNavigator
import com.elvishew.xlog.XLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val dispatcher: CommonDispatcherProvider,
    val navigator: MovieDbNavigator,
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val queryFlow = MutableSharedFlow<String>(replay = 0)

    private val _searchResultList = MutableStateFlow<List<Movie>>(listOf())
    val searchResultList: StateFlow<List<Movie>> = _searchResultList.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentPage = 1
    private var hasNextPage = false

    init {
        viewModelScope.launch {
            queryFlow.onEach { q -> _query.update { q } }
                .transform {
                    clearResults()
                    emit(it)
                }
                .debounce(300L.milliseconds)
                .distinctUntilChanged()
                .filter { it.isNotEmpty() }
                .collectLatest {
                    search()
                }
        }
    }

    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            queryFlow.emit(query)
        }
    }

    private fun search(isLoadMore: Boolean = false) {
        viewModelScope.launch {
            searchMoviesUseCase(
                query = _query.value,
                page = currentPage
            )
                .flowOn(dispatcher.io)
                .collect { response ->
                    when (response) {
                        is Resource.Loading -> {
                            if (isLoadMore) {
                                _isLoadingMore.update { true }
                            } else {
                                _isLoading.update { true }
                            }
                        }

                        is Resource.Error -> {
                            _isLoadingMore.update { false }
                            _isLoading.update { false }
                            XLog.d("${response.exception}")
                        }

                        is Resource.Success -> {
                            if (isLoadMore) {
                                _isLoadingMore.update { false }
                            } else {
                                _isLoading.update { false }
                            }

                            hasNextPage = response.data.hasNextPage

                            _searchResultList.update { oldList ->
                                oldList + response.data.movies
                            }
                        }
                    }
                }
        }
    }

    fun loadMore() {
        if (hasNextPage) {
            currentPage += 1
            search(isLoadMore = true)
        }
    }

    fun clearQuery() {
        _query.value = ""

        viewModelScope.launch {
            queryFlow.emit("")
        }

        clearResults()
    }

    private fun clearResults() {
        _searchResultList.update { emptyList() }
        currentPage = 1
        hasNextPage = false
    }
}