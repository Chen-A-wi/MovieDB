package com.awilab.network.service

import com.awilab.network.BuildConfig
import com.awilab.network.model.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("append_to_response") appendToResponse: String = "credits,images,videos"
    ): MovieDetail
}