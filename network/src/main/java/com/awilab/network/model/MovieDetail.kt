package com.awilab.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    @SerialName("adult")
    val adult: Boolean? = false,
    @SerialName("backdrop_path")
    val backdropPath: String? = "",
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = BelongsToCollection(),
    @SerialName("budget")
    val budget: Int? = 0,
    @SerialName("credits")
    val credits: Credits? = Credits(),
    @SerialName("genres")
    val genres: List<Genre?>? = listOf(),
    @SerialName("homepage")
    val homepage: String? = "",
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("images")
    val images: Images? = Images(),
    @SerialName("imdb_id")
    val imdbId: String? = "",
    @SerialName("origin_country")
    val originCountry: List<String?>? = listOf(),
    @SerialName("original_language")
    val originalLanguage: String? = "",
    @SerialName("original_title")
    val originalTitle: String? = "",
    @SerialName("overview")
    val overview: String? = "",
    @SerialName("popularity")
    val popularity: Double? = 0.0,
    @SerialName("poster_path")
    val posterPath: String? = "",
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany?>? = listOf(),
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry?>? = listOf(),
    @SerialName("release_date")
    val releaseDate: String? = "",
    @SerialName("revenue")
    val revenue: Int? = 0,
    @SerialName("runtime")
    val runtime: Int? = 0,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>? = listOf(),
    @SerialName("status")
    val status: String? = "",
    @SerialName("tagline")
    val tagline: String? = "",
    @SerialName("title")
    val title: String? = "",
    @SerialName("video")
    val video: Boolean? = false,
    @SerialName("videos")
    val videos: Videos? = Videos(),
    @SerialName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerialName("vote_count")
    val voteCount: Int? = 0
) {
    @Serializable
    data class BelongsToCollection(
        @SerialName("backdrop_path")
        val backdropPath: String? = "",
        @SerialName("id")
        val id: Int? = 0,
        @SerialName("name")
        val name: String? = "",
        @SerialName("poster_path")
        val posterPath: String? = ""
    )

    @Serializable
    data class Credits(
        @SerialName("cast")
        val cast: List<Cast?>? = listOf(),
        @SerialName("crew")
        val crew: List<Crew?>? = listOf()
    ) {
        @Serializable
        data class Cast(
            @SerialName("adult")
            val adult: Boolean? = false,
            @SerialName("cast_id")
            val castId: Int? = 0,
            @SerialName("character")
            val character: String? = "",
            @SerialName("credit_id")
            val creditId: String? = "",
            @SerialName("gender")
            val gender: Int? = 0,
            @SerialName("id")
            val id: Int? = 0,
            @SerialName("known_for_department")
            val knownForDepartment: String? = "",
            @SerialName("name")
            val name: String? = "",
            @SerialName("order")
            val order: Int? = 0,
            @SerialName("original_name")
            val originalName: String? = "",
            @SerialName("popularity")
            val popularity: Double? = 0.0,
            @SerialName("profile_path")
            val profilePath: String? = ""
        )

        @Serializable
        data class Crew(
            @SerialName("adult")
            val adult: Boolean? = false,
            @SerialName("credit_id")
            val creditId: String? = "",
            @SerialName("department")
            val department: String? = "",
            @SerialName("gender")
            val gender: Int? = 0,
            @SerialName("id")
            val id: Int? = 0,
            @SerialName("job")
            val job: String? = "",
            @SerialName("known_for_department")
            val knownForDepartment: String? = "",
            @SerialName("name")
            val name: String? = "",
            @SerialName("original_name")
            val originalName: String? = "",
            @SerialName("popularity")
            val popularity: Double? = 0.0,
            @SerialName("profile_path")
            val profilePath: String? = ""
        )
    }

    @Serializable
    data class Genre(
        @SerialName("id")
        val id: Int? = 0,
        @SerialName("name")
        val name: String? = ""
    )

    // TODO: fix images api fail
    @Serializable
    data class Images(
        @SerialName("backdrops")
        val backdrops: List<Backdrop?>? = listOf(),
        @SerialName("logos")
        val logos: List<Logo?>? = listOf(),
        @SerialName("posters")
        val posters: List<Poster?>? = listOf()
    ) {
        @Serializable
        data class Backdrop(
            @SerialName("aspect_ratio")
            val aspectRatio: Double? = 0.0,
            @SerialName("file_path")
            val filePath: String? = "",
            @SerialName("height")
            val height: Int? = 0,
            @SerialName("iso_3166_1")
            val iso31661: String? = "",
            @SerialName("iso_639_1")
            val iso6391: String? = "",
            @SerialName("vote_average")
            val voteAverage: Double? = 0.0,
            @SerialName("vote_count")
            val voteCount: Int? = 0,
            @SerialName("width")
            val width: Int? = 0
        )

        @Serializable
        data class Logo(
            @SerialName("aspect_ratio")
            val aspectRatio: Double? = 0.0,
            @SerialName("file_path")
            val filePath: String? = "",
            @SerialName("height")
            val height: Int? = 0,
            @SerialName("iso_3166_1")
            val iso31661: String? = "",
            @SerialName("iso_639_1")
            val iso6391: String? = "",
            @SerialName("vote_average")
            val voteAverage: Double? = 0.0,
            @SerialName("vote_count")
            val voteCount: Int? = 0,
            @SerialName("width")
            val width: Int? = 0
        )

        @Serializable
        data class Poster(
            @SerialName("aspect_ratio")
            val aspectRatio: Double? = 0.0,
            @SerialName("file_path")
            val filePath: String? = "",
            @SerialName("height")
            val height: Int? = 0,
            @SerialName("iso_3166_1")
            val iso31661: String? = "",
            @SerialName("iso_639_1")
            val iso6391: String? = "",
            @SerialName("vote_average")
            val voteAverage: Double? = 0.0,
            @SerialName("vote_count")
            val voteCount: Int? = 0,
            @SerialName("width")
            val width: Int? = 0
        )
    }

    @Serializable
    data class ProductionCompany(
        @SerialName("id")
        val id: Int? = 0,
        @SerialName("logo_path")
        val logoPath: String? = "",
        @SerialName("name")
        val name: String? = "",
        @SerialName("origin_country")
        val originCountry: String? = ""
    )

    @Serializable
    data class ProductionCountry(
        @SerialName("iso_3166_1")
        val iso31661: String? = "",
        @SerialName("name")
        val name: String? = ""
    )

    @Serializable
    data class SpokenLanguage(
        @SerialName("english_name")
        val englishName: String? = "",
        @SerialName("iso_639_1")
        val iso6391: String? = "",
        @SerialName("name")
        val name: String? = ""
    )

    @Serializable
    data class Videos(
        @SerialName("results")
        val results: List<Result?>? = listOf()
    ) {
        @Serializable
        data class Result(
            @SerialName("id")
            val id: String? = "",
            @SerialName("iso_3166_1")
            val iso31661: String? = "",
            @SerialName("iso_639_1")
            val iso6391: String? = "",
            @SerialName("key")
            val key: String? = "",
            @SerialName("name")
            val name: String? = "",
            @SerialName("official")
            val official: Boolean? = false,
            @SerialName("published_at")
            val publishedAt: String? = "",
            @SerialName("site")
            val site: String? = "",
            @SerialName("size")
            val size: Int? = 0,
            @SerialName("type")
            val type: String? = ""
        )
    }
}