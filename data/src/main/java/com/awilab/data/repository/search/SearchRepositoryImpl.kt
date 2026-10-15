package com.awilab.data.repository.search

import com.awilab.data.common.asResource
import com.awilab.data.mapper.toDomain
import com.awilab.domain.model.MoviePage
import com.awilab.domain.model.Resource
import com.awilab.domain.repository.SearchRepository
import com.awilab.network.service.SearchService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
) : SearchRepository {

    override suspend fun searchMovie(
        query: String,
        page: Int,
        language: String,
    ): Flow<Resource<MoviePage>> {
        return flow {
            searchService.searchMovie(
                query = query,
                page = page,
                language = language,
            ).also { response ->
                emit(response.toDomain())
            }
        }.asResource()
    }

}
