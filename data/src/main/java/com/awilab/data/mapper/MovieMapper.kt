package com.awilab.data.mapper

import com.awilab.common.extension.orZero
import com.awilab.domain.model.Cast
import com.awilab.domain.model.Movie
import com.awilab.domain.model.MovieDetail
import com.awilab.domain.model.MoviePage
import com.awilab.network.model.SearchResults
import com.awilab.network.model.MovieDetail as MovieDetailDto

fun SearchResults.toDomain(): MoviePage = MoviePage(
    page = page.orZero(),
    totalPages = totalPages.orZero(),
    movies = results.orEmpty()
        .filterNotNull()
        .filter { it.id.orZero() > 0 }
        .map { it.toDomain() },
)

fun SearchResults.Result.toDomain(): Movie = Movie(
    id = id.orZero(),
    title = title.orEmpty(),
    posterPath = posterPath.nullIfBlank(),
)

fun MovieDetailDto.toDomain(): MovieDetail = MovieDetail(
    id = id.orZero(),
    title = title.orEmpty(),
    overview = overview.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    posterPath = posterPath.nullIfBlank(),
    backdropPath = backdropPath.nullIfBlank(),
    originCountry = originCountry.orEmpty().filterNotNull(),
    cast = credits?.cast.orEmpty().filterNotNull().map { it.toDomain() },
)

fun MovieDetailDto.Credits.Cast.toDomain(): Cast = Cast(
    id = id.orZero(),
    name = name.orEmpty(),
    character = character.orEmpty(),
    profilePath = profilePath.nullIfBlank(),
)

private fun String?.nullIfBlank(): String? = this?.takeIf { it.isNotBlank() }
