package com.awilab.domain.repository

import com.awilab.network.ApiResponse
import com.awilab.network.model.SearchResults
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchMovie(
        query: String,
    ): Flow<ApiResponse<SearchResults>>
}