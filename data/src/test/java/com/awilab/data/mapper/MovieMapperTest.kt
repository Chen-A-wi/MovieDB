package com.awilab.data.mapper

import com.awilab.domain.model.Cast
import com.awilab.domain.model.Movie
import com.awilab.network.model.MovieDetail
import com.awilab.network.model.SearchResults
import com.awilab.testing.shouldBe
import org.junit.Test

class MovieMapperTest {

    @Test
    fun `SearchResults maps to MoviePage and drops null or id-less results`() {
        val dto = SearchResults(
            page = 2,
            totalPages = 5,
            results = listOf(
                SearchResults.Result(id = 1, title = "A", posterPath = "/a.jpg"),
                null,
                SearchResults.Result(id = null, title = "no id"),
                SearchResults.Result(id = 0, title = "zero id"),
                SearchResults.Result(id = 2, title = null, posterPath = "  "),
            ),
        )

        val page = dto.toDomain()

        page.page shouldBe 2
        page.totalPages shouldBe 5
        page.movies shouldBe listOf(Movie(1, "A", "https://image.tmdb.org/t/p/w500/a.jpg"), Movie(2, "", null))
    }

    @Test
    fun `MovieDetail maps nested cast and origin country`() {
        val dto = MovieDetail(
            id = 10,
            title = "T",
            originCountry = listOf("US", null),
            credits = MovieDetail.Credits(
                cast = listOf(
                    MovieDetail.Credits.Cast(id = 3, name = "N", character = "C", profilePath = "/p.jpg"),
                    null,
                ),
            ),
        )

        val detail = dto.toDomain()

        detail.id shouldBe 10
        detail.title shouldBe "T"
        detail.originCountry shouldBe listOf("US")
        detail.cast shouldBe listOf(Cast(3, "N", "C", "https://image.tmdb.org/t/p/w500/p.jpg"))
    }

    @Test
    fun `MovieDetail with null credits maps to empty cast`() {
        val dto = MovieDetail(credits = null)

        val detail = dto.toDomain()

        detail.cast shouldBe emptyList()
    }

    @Test
    fun `blank or null image paths map to null`() {
        val dto = MovieDetail(posterPath = "", backdropPath = null)

        val detail = dto.toDomain()

        detail.posterUrl shouldBe null
        detail.backdropUrl shouldBe null
    }

    @Test
    fun `blank cast profile path maps to null`() {
        val dto = MovieDetail.Credits.Cast(profilePath = " ")

        val cast = dto.toDomain()

        cast.profileUrl shouldBe null
    }

    @Test
    fun `image paths map to full urls with size`() {
        val dto = MovieDetail(posterPath = "/p.jpg", backdropPath = "/b.jpg")

        val detail = dto.toDomain()

        detail.posterUrl shouldBe "https://image.tmdb.org/t/p/w500/p.jpg"
        detail.backdropUrl shouldBe "https://image.tmdb.org/t/p/original/b.jpg"
    }
}
