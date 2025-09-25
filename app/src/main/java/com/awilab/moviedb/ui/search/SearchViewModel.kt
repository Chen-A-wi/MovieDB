package com.awilab.moviedb.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awilab.common.coroutine.CommonDispatcherProvider
import com.awilab.domain.repository.SearchRepository
import com.awilab.moviedb.common.navigation.MovieDbNavigator
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
    val queryFlow = _queryFlow.asSharedFlow()

    private val _searchResultList = MutableStateFlow<MutableList<SearchResults>>(mutableListOf())
    val searchResultList: StateFlow<MutableList<SearchResults>> = _searchResultList.asStateFlow()

    init {
        viewModelScope.launch {
            queryFlow.onEach { q -> _query.update { q } }
                .debounce(500L)
                .distinctUntilChanged()
                .collectLatest { search() }
        }
    }

    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            _queryFlow.emit(query)
        }
    }

    private fun search() {
        XLog.d("@@@@@@@@@@@ ${_query.value} @@@@@@@@@@")

//        viewModelScope.launch {
//            searchRepository.searchMovie(
//                query = _keyword.value,
//                page = 1
//            )
//                .flowOn(dispatcher.io)
//                .collect { response ->
//                    when (response) {
//                        is ApiResponse.Loading -> {
//                            XLog.d("================= isLoading ==================")
//                        }
//
//                        is ApiResponse.Error -> {
//                            XLog.d("================= Error: ${response.exception}")
//                        }
//
//                        is ApiResponse.Success -> {
//                            XLog.d("================= Success: ${response.data}")
//
//                        }
//
//                        else -> {
//                            XLog.d("================= else ==================")
//                        }
//                    }
//                }
//        }
    }

    fun clearQuery() {
        _query.update { "" }
        _searchResultList.update { mutableListOf() }
    }
}