package com.awilab.data.repository.detail

import com.awilab.data.common.asResource
import com.awilab.data.mapper.toDomain
import com.awilab.domain.model.MovieDetail
import com.awilab.domain.model.Resource
import com.awilab.domain.repository.DetailRepository
import com.awilab.network.service.DetailService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailService: DetailService,
) : DetailRepository {

    override fun getMovieDetail(
        movieId: Int,
        language: String
    ): Flow<Resource<MovieDetail>> {
        return flow {
            detailService.getMovieDetail(
                movieId = movieId.toString(),
                language = language,
            ).also { response ->
                emit(response.toDomain())
            }
        }.asResource()
    }

}
