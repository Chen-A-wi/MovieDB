package com.awilab.data.repository.detail

import com.awilab.domain.repository.DetailRepository
import com.awilab.network.ApiResponse
import com.awilab.network.asApiResponse
import com.awilab.network.model.MovieDetail
import com.awilab.network.service.DetailService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailService: DetailService,
) : DetailRepository {

    override suspend fun getMovieDetail(
        movieId: String,
        language: String
    ): Flow<ApiResponse<MovieDetail>> {
        return flow {
            detailService.getMovieDetail(
                movieId = movieId,
                language = language,
            ).also { response ->
                emit(response)
            }
        }.asApiResponse()
    }

}