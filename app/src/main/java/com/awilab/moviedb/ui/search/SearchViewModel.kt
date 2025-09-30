package com.awilab.moviedb.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awilab.common.coroutine.CommonDispatcherProvider
import com.awilab.domain.repository.SearchRepository
import com.awilab.moviedb.common.navigation.MovieDbNavigator
import com.awilab.network.ApiResponse
import com.awilab.network.model.SearchResults
import com.elvishew.xlog.XLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val dispatcher: CommonDispatcherProvider,
    val navigator: MovieDbNavigator
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _queryFlow = MutableSharedFlow<String>(replay = 0)
    private val queryFlow = _queryFlow.asSharedFlow()

    private val _searchResultList = MutableStateFlow<List<SearchResults.Result>>(listOf())
    val searchResultList: StateFlow<List<SearchResults.Result>> = _searchResultList.asStateFlow()

    init {
        viewModelScope.launch {
            queryFlow.onEach { q -> _query.update { q } }
                .debounce(500L)
                .distinctUntilChanged()
                .collectLatest {
                    _searchResultList.value = emptyList()   // clean list
                    search()
                }
        }
    }

    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            _queryFlow.emit(query)
        }
    }

    private fun search() {
        viewModelScope.launch {
            searchRepository.searchMovie(
                query = _query.value,
                page = 1
            )
                .flowOn(dispatcher.io)
                .collect { response ->
                    when (response) {
                        is ApiResponse.Loading -> {
                            XLog.d("================= isLoading ==================")
                        }

                        is ApiResponse.Error -> {
                            XLog.d("================= Error: ${response.exception}")
                        }

                        is ApiResponse.Success -> {
                            response.data.results?.let {
                                _searchResultList.update { oldList ->
                                    oldList + it.filterNotNull()
                                }
                            }
                        }

                        else -> {
                            XLog.d("================= else ==================")
                        }
                    }
                }
        }
    }

    fun clearQuery() {
        _query.update { "" }
        _searchResultList.update { mutableListOf() }
    }
}