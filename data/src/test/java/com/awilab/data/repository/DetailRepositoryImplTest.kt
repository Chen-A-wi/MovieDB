package com.awilab.data.repository

import com.awilab.data.repository.detail.DetailRepositoryImpl
import com.awilab.domain.model.MovieDetail
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
import java.net.SocketTimeoutException

class DetailRepositoryImplTest {

    private lateinit var server: MockWebServer
    private lateinit var repository: DetailRepositoryImpl

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        repository = DetailRepositoryImpl(server.createService())
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private suspend fun getDetail(movieId: Int = 42) =
        repository.getMovieDetail(movieId = movieId, language = "en-US").toList()

    @Test
    fun `success emits Loading then mapped detail and puts id in path`() = runTest {
        server.enqueueJson("""{"id":42,"title":"T","unknown_field":{"a":1}}""")

        getDetail() shouldBe listOf(Resource.Loading, Resource.Success(MovieDetail(id = 42, title = "T")))
        server.takeRequest().requestUrl!!.encodedPath shouldBe "/movie/42"
    }

    @Test
    fun `unknown movie id 404 emits Error with HttpException`() = runTest {
        server.enqueueJson(tmdbError(34, "The resource you requested could not be found."), code = 404)

        getDetail(movieId = -1).shouldBeLoadingThenError<HttpException>().code() shouldBe 404
    }

    @Test
    fun `500 emits Error with HttpException`() = runTest {
        server.enqueueJson("", code = 500)

        getDetail().shouldBeLoadingThenError<HttpException>().code() shouldBe 500
    }

    @Test
    fun `malformed json emits Error with SerializationException`() = runTest {
        server.enqueueJson("<html>gateway error</html>")

        getDetail().shouldBeLoadingThenError<SerializationException>()
    }

    @Test
    fun `no response emits Error with SocketTimeoutException`() = runTest {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))

        getDetail().shouldBeLoadingThenError<SocketTimeoutException>()
    }

    @Test
    fun `null credits and countries map to empty lists`() = runTest {
        server.enqueueJson("""{"id":42,"credits":null,"origin_country":null}""")

        getDetail() shouldBe listOf(Resource.Loading, Resource.Success(MovieDetail(id = 42)))
    }
}
