package com.awilab.domain.repository

import com.awilab.network.ApiResponse
import com.awilab.network.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchMovieList(
        query: String,
    ): Flow<ApiResponse<SearchResponse>>
}