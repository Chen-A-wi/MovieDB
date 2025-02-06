package com.awilab.data.repository.search

import com.awilab.domain.repository.SearchRepository
import com.awilab.network.ApiResponse
import com.awilab.network.asApiResponse
import com.awilab.network.model.SearchResults
import com.awilab.network.service.SearchService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
) : SearchRepository {

    override fun searchMovie(
        query: String,
    ): Flow<ApiResponse<SearchResults>> {
        return flow {
            searchService.searchMovie(query).also { response ->
                emit(response)
            }
        }.asApiResponse()
    }

}