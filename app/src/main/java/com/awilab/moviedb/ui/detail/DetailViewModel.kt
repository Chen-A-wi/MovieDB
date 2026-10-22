package com.awilab.moviedb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awilab.common.coroutine.CommonDispatcherProvider
import com.awilab.domain.model.MovieDetail
import com.awilab.domain.model.Resource
import com.awilab.domain.usecase.GetMovieDetailUseCase
import com.awilab.moviedb.common.navigation.DetailRoute
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted private val route: DetailRoute,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val dispatcher: CommonDispatcherProvider,
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(route: DetailRoute): DetailViewModel
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading:StateFlow<Boolean> = _isLoading

    private val _movieInfo = MutableStateFlow(MovieDetail())
    val movieInfo:StateFlow<MovieDetail> = _movieInfo.asStateFlow()


    init {
        getMovieDetail(route.movieId)
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase(movieId = movieId)
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