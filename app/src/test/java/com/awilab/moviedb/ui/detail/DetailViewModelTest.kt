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
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class DetailViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private fun TestScope.loadDetail(response: Flow<Resource<MovieDetail>>) = DetailViewModel(
        route = DetailRoute(movieId = 42),
        getMovieDetailUseCase = GetMovieDetailUseCase(
            object : DetailRepository {
                override fun getMovieDetail(movieId: Int, language: String) = response
            },
        ),
        dispatcher = TestDispatcherProvider(mainRule.dispatcher),
    ).also { advanceUntilIdle() }

    @Test
    fun `error resets loading and keeps the empty detail`() = runTest(mainRule.dispatcher) {
        val response = flow { emit(Resource.Loading); emit(Resource.Error(IOException("HTTP 404"))) }

        val vm = loadDetail(response)

        vm.isLoading.value shouldBe false
        vm.movieInfo.value shouldBe MovieDetail()
    }

    @Test
    fun `loading is true while request is in flight`() = runTest(mainRule.dispatcher) {
        val response = gated(CompletableDeferred())

        val vm = loadDetail(response)

        vm.isLoading.value shouldBe true
    }

    @Test
    fun `loading is false and detail is shown after success`() = runTest(mainRule.dispatcher) {
        val gate = CompletableDeferred<Unit>()
        val vm = loadDetail(gated(gate))

        gate.complete(Unit)
        advanceUntilIdle()

        vm.isLoading.value shouldBe false
        vm.movieInfo.value shouldBe MovieDetail(id = 42, title = "T")
    }

    private fun gated(gate: CompletableDeferred<Unit>): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading)
        gate.await()
        emit(Resource.Success(MovieDetail(id = 42, title = "T")))
    }
}
