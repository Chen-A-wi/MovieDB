package com.awilab.moviedb.ui.detail

import com.awilab.domain.model.MovieDetail
import com.awilab.domain.model.Resource
import com.awilab.domain.repository.DetailRepository
import com.awilab.domain.usecase.GetMovieDetailUseCase
import com.awilab.moviedb.common.navigation.DetailRoute
import com.awilab.moviedb.ui.TestDispatcherProvider
import com.awilab.testing.MainDispatcherRule
import com.awilab.testing.shouldBe
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class DetailViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private fun createViewModel(response: Flow<Resource<MovieDetail>>) = DetailViewModel(
        route = DetailRoute(movieId = 42),
        getMovieDetailUseCase = GetMovieDetailUseCase(
            object : DetailRepository {
                override fun getMovieDetail(movieId: Int, language: String) = response
            },
        ),
        dispatcher = TestDispatcherProvider(mainRule.dispatcher),
    )

    @Test
    fun `error resets loading and keeps the empty detail`() = runTest(mainRule.dispatcher) {
        val vm = createViewModel(flow { emit(Resource.Loading); emit(Resource.Error(IOException("HTTP 404"))) })

        advanceUntilIdle()

        vm.isLoading.value shouldBe false
        vm.movieInfo.value shouldBe MovieDetail()
    }

    @Test
    fun `loading is true while request is in flight and false after success`() = runTest(mainRule.dispatcher) {
        val gate = CompletableDeferred<Unit>()
        val vm = createViewModel(
            flow {
                emit(Resource.Loading)
                gate.await()
                emit(Resource.Success(MovieDetail(id = 42, title = "T")))
            },
        )

        advanceUntilIdle()
        vm.isLoading.value shouldBe true

        gate.complete(Unit)
        advanceUntilIdle()

        vm.isLoading.value shouldBe false
        vm.movieInfo.value shouldBe MovieDetail(id = 42, title = "T")
    }
}
