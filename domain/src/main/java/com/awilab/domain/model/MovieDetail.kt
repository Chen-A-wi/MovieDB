package com.awilab.domain.model

data class MovieDetail(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val originCountry: List<String> = emptyList(),
    val cast: List<Cast> = emptyList(),
)
