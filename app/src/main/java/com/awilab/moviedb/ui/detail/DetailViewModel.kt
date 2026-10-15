package com.awilab.moviedb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awilab.common.coroutine.CommonDispatcherProvider
import com.awilab.data.local.search.SearchToDetailArgs
import com.awilab.domain.model.MovieDetail
import com.awilab.domain.model.Resource
import com.awilab.domain.repository.DetailRepository
import com.awilab.moviedb.common.navigation.MovieDbNavigator
import com.awilab.moviedb.common.navigation.NavScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    private val dispatcher: CommonDispatcherProvider,
    val navigator: MovieDbNavigator
) : ViewModel() {
    private val _movieId = MutableStateFlow(0)
    val movieId: StateFlow<Int> = _movieId.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading:StateFlow<Boolean> = _isLoading

    private val _movieInfo = MutableStateFlow(MovieDetail())
    val movieInfo:StateFlow<MovieDetail> = _movieInfo.asStateFlow()


    fun initMovieDetail() {
        navigator.getNavBundle<SearchToDetailArgs>(NavScreen.Search.ARG_RESULT)?.run {
            getMovieDetail(this.movieId)
            _movieId.update { this.movieId }
        }
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            detailRepository.getMovieDetail(
                movieId = movieId.toString(),
            )
                .flowOn(dispatcher.io)
                .collect { response ->
                    when(response) {
                        is Resource.Loading -> {
                            _isLoading.update { true }
                        }
                        is Resource.Success -> {
                            _isLoading.update { false }
                            _movieInfo.update { response.data }
                        }
                        is Resource.Error -> {
                            _isLoading.update { false }
                        }
                    }
                }
        }
    }
}