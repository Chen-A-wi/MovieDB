package com.awilab.domain.repository

import com.awilab.domain.model.MovieDetail
import com.awilab.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getMovieDetail(
        movieId: String,
        language: String = "en",
    ): Flow<Resource<MovieDetail>>
}
