package com.awilab.domain.usecase

import com.awilab.domain.model.MovieDetail
import com.awilab.domain.model.Resource
import com.awilab.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMovieDetailUseCaseTest {

    @Test
    fun `movie id is forwarded to repository`() = runBlocking {
        var requestedId: Int? = null
        val repository = object : DetailRepository {
            override suspend fun getMovieDetail(
                movieId: Int,
                language: String,
            ): Flow<Resource<MovieDetail>> {
                requestedId = movieId
                return flowOf(Resource.Success(MovieDetail(id = movieId)))
            }
        }

        val result = GetMovieDetailUseCase(repository)(42).toList()

        assertEquals(42, requestedId)
        assertEquals(listOf(Resource.Success(MovieDetail(id = 42))), result)
    }
}
