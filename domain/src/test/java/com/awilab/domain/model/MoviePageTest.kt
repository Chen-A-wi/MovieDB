package com.awilab.domain.model

import com.awilab.testing.shouldBe
import org.junit.Test

class MoviePageTest {

    @Test
    fun `hasNextPage is true before last page`() {
        val page = MoviePage(page = 2, totalPages = 3, movies = emptyList())

        val hasNext = page.hasNextPage

        hasNext shouldBe true
    }

    @Test
    fun `hasNextPage is false on last page`() {
        val page = MoviePage(page = 3, totalPages = 3, movies = emptyList())

        val hasNext = page.hasNextPage

        hasNext shouldBe false
    }

    @Test
    fun `hasNextPage is false on empty result`() {
        val page = MoviePage(page = 0, totalPages = 0, movies = emptyList())

        val hasNext = page.hasNextPage

        hasNext shouldBe false
    }
}
