package com.awilab.domain.model

data class MoviePage(
    val page: Int,
    val totalPages: Int,
    val movies: List<Movie>,
) {
    val hasNextPage: Boolean
        get() = page < totalPages
}
