package com.awilab.network.service

import com.awilab.network.model.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String
    ): MovieDetail
}