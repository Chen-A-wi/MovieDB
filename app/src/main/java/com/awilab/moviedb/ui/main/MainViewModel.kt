package com.awilab.moviedb.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awilab.domain.repository.SearchRepository
import com.awilab.network.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    fun test() {
        viewModelScope.launch {
            searchRepository.searchMovie("Jack")
                .collect { response ->
                    when(response) {
                        is ApiResponse.Loading -> {
                            println("================= isLoading ==================")
                        }

                        is ApiResponse.Error -> {
                            println("================= Error: ${response.exception}")
                        }

                        is ApiResponse.Success -> {
                            println("================= Success: ${response.data}")
                        }
                    }
                }
        }
    }
}