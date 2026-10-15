package com.awilab.domain.repository

import com.awilab.domain.model.MoviePage
import com.awilab.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchMovie(
        query: String,
        page: Int,
        language: String = "en",
    ): Flow<Resource<MoviePage>>
}
