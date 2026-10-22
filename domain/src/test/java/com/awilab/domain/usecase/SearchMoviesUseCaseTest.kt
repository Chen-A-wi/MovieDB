package com.awilab.domain.usecase

import com.awilab.domain.model.MoviePage
import com.awilab.domain.model.Resource
import com.awilab.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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

        assertEquals("batman", repository.lastQuery)
        assertEquals(2, repository.lastPage)
        assertEquals(listOf(Resource.Success(MoviePage(2, 3, emptyList()))), result)
    }

    @Test
    fun `hasNextPage is false on last page`() {
        assertTrue(MoviePage(page = 2, totalPages = 3, movies = emptyList()).hasNextPage)
        assertFalse(MoviePage(page = 3, totalPages = 3, movies = emptyList()).hasNextPage)
        assertFalse(MoviePage(page = 0, totalPages = 0, movies = emptyList()).hasNextPage)
    }
}
