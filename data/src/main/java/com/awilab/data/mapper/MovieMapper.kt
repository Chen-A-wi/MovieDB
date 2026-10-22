package com.awilab.data.mapper

import com.awilab.common.extension.orZero
import com.awilab.domain.model.Cast
import com.awilab.domain.model.Movie
import com.awilab.domain.model.MovieDetail
import com.awilab.domain.model.MoviePage
import com.awilab.network.di.BASE_IMAGE_URL
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
    posterUrl = posterPath.toImageUrl(POSTER_SIZE),
)

fun MovieDetailDto.toDomain(): MovieDetail = MovieDetail(
    id = id.orZero(),
    title = title.orEmpty(),
    overview = overview.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    posterUrl = posterPath.toImageUrl(POSTER_SIZE),
    backdropUrl = backdropPath.toImageUrl(BACKDROP_SIZE),
    originCountry = originCountry.orEmpty().filterNotNull(),
    cast = credits?.cast.orEmpty().filterNotNull().map { it.toDomain() },
)

fun MovieDetailDto.Credits.Cast.toDomain(): Cast = Cast(
    id = id.orZero(),
    name = name.orEmpty(),
    character = character.orEmpty(),
    profileUrl = profilePath.toImageUrl(PROFILE_SIZE),
)

private const val POSTER_SIZE = "w500"
private const val BACKDROP_SIZE = "original"
private const val PROFILE_SIZE = "w500"

// 路徑為 null 或空白時回傳 null，代表沒有圖片
private fun String?.toImageUrl(size: String): String? =
    this?.takeIf { it.isNotBlank() }?.let { "${BASE_IMAGE_URL}t/p/$size$it" }
