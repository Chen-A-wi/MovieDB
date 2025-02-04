package com.awilab.network.service

import com.awilab.network.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int? = 1,
        @Query("language") language: String = "en",
        @Query("api_key") apiKey: String = "30475441afb1c0a41ed69133fe8b35af",
    ): SearchResponse
}