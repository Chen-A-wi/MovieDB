package com.awilab.moviedb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awilab.common.coroutine.CommonDispatcherProvider
import com.awilab.domain.repository.SearchRepository
import com.awilab.moviedb.common.navigation.MovieDbNavigator
import com.awilab.network.ApiResponse
import com.elvishew.xlog.XLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val  navigator: MovieDbNavigator,
    private val searchRepository: SearchRepository,
    private val dispatcher: CommonDispatcherProvider,
) : ViewModel() {
    fun test() {
        viewModelScope.launch {
            searchRepository.searchMovie("Jack")
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
                            XLog.d("================= Success: ${response.data}")
                        }

                        else -> {
                            XLog.d("================= else ==================")
                        }
                    }
                }
        }
    }
}