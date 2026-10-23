package com.awilab.data.repository

import com.awilab.data.repository.search.SearchRepositoryImpl
import com.awilab.domain.model.Movie
import com.awilab.domain.model.MoviePage
import com.awilab.domain.model.Resource
import com.awilab.testing.shouldBe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class SearchRepositoryImplTest {

    private lateinit var server: MockWebServer
    private lateinit var repository: SearchRepositoryImpl

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        repository = SearchRepositoryImpl(server.createService())
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private suspend fun search() = repository.searchMovie(query = "batman", page = 2, language = "en-US").toList()

    @Test
    fun `success emits Loading then mapped page`() = runTest {
        server.enqueueJson("""{"page":2,"total_pages":3,"results":[{"id":1,"title":"A","poster_path":"/a.jpg"}]}""")

        val result = search()

        result shouldBe listOf(
            Resource.Loading,
            Resource.Success(MoviePage(2, 3, listOf(Movie(1, "A", "https://image.tmdb.org/t/p/w500/a.jpg")))),
        )
    }

    @Test
    fun `search sends path and query params`() = runTest {
        server.enqueueJson("""{"results":[]}""")

        search()

        val url = server.takeRequest().requestUrl!!
        url.encodedPath shouldBe "/search/movie"
        url.queryParameter("query") shouldBe "batman"
        url.queryParameter("page") shouldBe "2"
        url.queryParameter("language") shouldBe "en-US"
    }

    @Test
    fun `404 emits Error with HttpException`() = runTest {
        server.enqueueJson(tmdbError(34, "The resource you requested could not be found."), code = 404)

        val result = search()

        result.shouldBeLoadingThenError<HttpException>().code() shouldBe 404
    }

    @Test
    fun `401 invalid api key emits Error with HttpException`() = runTest {
        server.enqueueJson(tmdbError(7, "Invalid API key: You must be granted a valid key."), code = 401)

        val result = search()

        result.shouldBeLoadingThenError<HttpException>().code() shouldBe 401
    }

    @Test
    fun `429 rate limit emits Error with HttpException`() = runTest {
        server.enqueueJson(tmdbError(25, "Your request count is over the allowed limit."), code = 429)

        val result = search()

        result.shouldBeLoadingThenError<HttpException>().code() shouldBe 429
    }

    @Test
    fun `500 emits Error with HttpException`() = runTest {
        server.enqueueJson("Internal Server Error", code = 500)

        val result = search()

        result.shouldBeLoadingThenError<HttpException>().code() shouldBe 500
    }

    @Test
    fun `malformed json emits Error with SerializationException`() = runTest {
        server.enqueueJson("""{"page":2,"results":[""")

        val result = search()

        result.shouldBeLoadingThenError<SerializationException>()
    }

    @Test
    fun `wrong field type emits Error with SerializationException`() = runTest {
        server.enqueueJson("""{"page":"two"}""")

        val result = search()

        result.shouldBeLoadingThenError<SerializationException>()
    }

    @Test
    fun `empty 200 body emits Error`() = runTest {
        server.enqueueJson("")

        val result = search()

        result.shouldBeLoadingThenError<SerializationException>()
    }

    @Test
    fun `204 no content emits Error`() = runTest {
        server.enqueue(MockResponse().setResponseCode(204))

        val result = search()

        result.shouldBeLoadingThenError<NullPointerException>()
    }

    @Test
    fun `connection dropped emits Error with IOException`() = runTest {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        val result = search()

        result.shouldBeLoadingThenError<IOException>()
    }

    @Test
    fun `no response emits Error with SocketTimeoutException`() = runTest {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))

        val result = search()

        result.shouldBeLoadingThenError<SocketTimeoutException>()
    }

    @Test
    fun `null results and missing fields map to empty page`() = runTest {
        server.enqueueJson("""{"results":null}""")

        val result = search()

        result shouldBe listOf(Resource.Loading, Resource.Success(MoviePage(0, 0, emptyList())))
    }
}
