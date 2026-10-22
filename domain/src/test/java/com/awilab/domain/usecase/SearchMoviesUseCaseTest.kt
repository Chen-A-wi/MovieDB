package com.awilab.domain.usecase

import com.awilab.domain.model.MoviePage
import com.awilab.domain.model.Resource
import com.awilab.domain.repository.SearchRepository
import com.awilab.testing.shouldBe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchMoviesUseCaseTest {

    private class FakeSearchRepository : SearchRepository {
        var lastQuery: String? = null
        var lastPage: Int? = null

        override fun searchMovie(
            query: String,
            page: Int,
            language: String,
        ): Flow<Resource<MoviePage>> {
            lastQuery = query
            lastPage = page
            return flowOf(Resource.Success(MoviePage(page, 3, emptyList())))
        }
    }

    @Test
    fun `query is trimmed and forwarded with page`() = runBlocking {
        val repository = FakeSearchRepository()

        val result = SearchMoviesUseCase(repository)("  batman ", 2).toList()

        repository.lastQuery shouldBe "batman"
        repository.lastPage shouldBe 2
        result shouldBe listOf(Resource.Success(MoviePage(2, 3, emptyList())))
    }

    @Test
    fun `hasNextPage is false on last page`() {
        MoviePage(page = 2, totalPages = 3, movies = emptyList()).hasNextPage shouldBe true
        MoviePage(page = 3, totalPages = 3, movies = emptyList()).hasNextPage shouldBe false
        MoviePage(page = 0, totalPages = 0, movies = emptyList()).hasNextPage shouldBe false
    }
}
