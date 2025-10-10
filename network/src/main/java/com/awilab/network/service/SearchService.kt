package com.awilab.network.service

import com.awilab.network.BuildConfig
import com.awilab.network.model.SearchResults
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): SearchResults
}