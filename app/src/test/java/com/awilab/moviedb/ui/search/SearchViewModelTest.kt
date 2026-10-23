package com.awilab.moviedb.ui.search

import com.awilab.domain.model.Movie
import com.awilab.domain.model.MoviePage
import com.awilab.domain.model.Resource
import com.awilab.domain.repository.SearchRepository
import com.awilab.domain.usecase.SearchMoviesUseCase
import com.awilab.moviedb.ui.TestDispatcherProvider
import com.awilab.testing.MainDispatcherRule
import com.awilab.testing.shouldBe
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.ConsolePrinter
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class SearchViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private data class Request(val query: String, val page: Int)

    private class FakeSearchRepository : SearchRepository {
        val requests = mutableListOf<Request>()
        var respond: (Request) -> Flow<Resource<MoviePage>> = { flowOf(success(it.page, totalPages = 1)) }

        override fun searchMovie(query: String, page: Int, language: String): Flow<Resource<MoviePage>> {
            val request = Request(query, page)
            requests += request
            return respond(request)
        }
    }

    private val repository = FakeSearchRepository()

    @Before
    fun initLogger() {
        XLog.init(LogConfiguration.Builder().build(), ConsolePrinter())
    }

    private fun TestScope.createViewModel() = SearchViewModel(
        searchMoviesUseCase = SearchMoviesUseCase(repository),
        dispatcher = TestDispatcherProvider(mainRule.dispatcher),
    ).also { runCurrent() }

    private fun TestScope.type(vm: SearchViewModel, query: String) {
        vm.onQueryChanged(query)
        advanceUntilIdle()
    }

    @Test
    fun `search error resets loading and leaves list empty`() = runTest(mainRule.dispatcher) {
        repository.respond = { error() }
        val vm = createViewModel()

        type(vm, "batman")

        vm.isLoading.value shouldBe false
        vm.searchResultList.value shouldBe emptyList()
    }

    @Test
    fun `loading is true while request is in flight and false after success`() = runTest(mainRule.dispatcher) {
        val gate = CompletableDeferred<Unit>()
        repository.respond = { gated(gate, success(it.page, totalPages = 1)) }
        val vm = createViewModel()

        type(vm, "batman")
        vm.isLoading.value shouldBe true

        gate.complete(Unit)
        advanceUntilIdle()

        vm.isLoading.value shouldBe false
        vm.searchResultList.value shouldBe movies(page = 1)
    }

    @Test
    fun `clearing query while loading resets loading`() = runTest(mainRule.dispatcher) {
        repository.respond = { gated(CompletableDeferred(), success(it.page, totalPages = 1)) }
        val vm = createViewModel()
        type(vm, "batman")

        vm.clearQuery()
        advanceUntilIdle()

        vm.isLoading.value shouldBe false
        vm.query.value shouldBe ""
        vm.searchResultList.value shouldBe emptyList()
    }

    @Test
    fun `new query while previous is loading drops the stale results`() = runTest(mainRule.dispatcher) {
        val staleGate = CompletableDeferred<Unit>()
        repository.respond = { request ->
            if (request.query == "bat") {
                gated(staleGate, success(page = 1, totalPages = 1, idOffset = 100))
            } else {
                flow { emit(Resource.Loading); emit(success(page = 1, totalPages = 1)) }
            }
        }
        val vm = createViewModel()
        type(vm, "bat")

        type(vm, "batman")
        staleGate.complete(Unit)
        advanceUntilIdle()

        vm.searchResultList.value shouldBe movies(page = 1)
        vm.isLoading.value shouldBe false
    }

    @Test
    fun `load more failure resets isLoadingMore, keeps list and can retry`() = runTest(mainRule.dispatcher) {
        repository.respond = { if (it.page == 1) flowOf(success(1, totalPages = 3)) else error() }
        val vm = createViewModel()
        type(vm, "batman")

        vm.loadMore()
        advanceUntilIdle()

        vm.isLoadingMore.value shouldBe false
        vm.searchResultList.value shouldBe movies(page = 1)

        vm.loadMore()
        advanceUntilIdle()

        repository.requests.map { it.page } shouldBe listOf(1, 2, 2)
    }

    @Test
    fun `load more is ignored on the last page`() = runTest(mainRule.dispatcher) {
        repository.respond = { flowOf(success(it.page, totalPages = 1)) }
        val vm = createViewModel()
        type(vm, "batman")

        vm.loadMore()
        advanceUntilIdle()

        repository.requests.size shouldBe 1
    }

    @Test
    fun `load more is ignored while the next page is loading`() = runTest(mainRule.dispatcher) {
        repository.respond = {
            if (it.page == 1) flowOf(success(1, totalPages = 3)) else gated(CompletableDeferred(), success(it.page, 3))
        }
        val vm = createViewModel()
        type(vm, "batman")

        vm.loadMore()
        advanceUntilIdle()
        vm.loadMore()
        advanceUntilIdle()

        vm.isLoadingMore.value shouldBe true
        repository.requests.map { it.page } shouldBe listOf(1, 2)
    }

    @Test
    fun `retyping the same query within debounce searches again`() = runTest(mainRule.dispatcher) {
        repository.respond = { flowOf(success(it.page, totalPages = 1)) }
        val vm = createViewModel()
        type(vm, "batman")

        vm.onQueryChanged("batma")
        type(vm, "batman")

        vm.searchResultList.value shouldBe movies(page = 1)
    }

    private companion object {
        fun movies(page: Int, idOffset: Int = 0) = listOf(Movie(id = page + idOffset, title = "movie $page", posterUrl = null))

        fun success(page: Int, totalPages: Int, idOffset: Int = 0): Resource<MoviePage> =
            Resource.Success(MoviePage(page, totalPages, movies(page, idOffset)))

        fun error(): Flow<Resource<MoviePage>> = flowOf(Resource.Error(IOException("network down")))

        fun flowOf(result: Resource<MoviePage>): Flow<Resource<MoviePage>> = flow {
            emit(Resource.Loading)
            emit(result)
        }

        fun gated(gate: CompletableDeferred<Unit>, result: Resource<MoviePage>): Flow<Resource<MoviePage>> = flow {
            emit(Resource.Loading)
            gate.await()
            emit(result)
        }
    }
}
