package com.awilab.domain.usecase

import com.awilab.domain.model.MoviePage
import com.awilab.domain.model.Resource
import com.awilab.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    suspend operator fun invoke(
        query: String,
        page: Int,
    ): Flow<Resource<MoviePage>> {
        return searchRepository.searchMovie(
            query = query.trim(),
            page = page,
        )
    }
}
