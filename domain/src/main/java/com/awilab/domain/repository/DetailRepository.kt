package com.awilab.domain.repository

import com.awilab.network.ApiResponse
import com.awilab.network.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getMovieDetail(
        movieId: String,
        language: String = "en",
    ): Flow<ApiResponse<MovieDetail>>
}