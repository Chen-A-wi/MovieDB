package com.awilab.data.repository

import com.awilab.domain.model.Resource
import com.awilab.network.di.NetworkModule
import com.awilab.testing.shouldBe
import com.awilab.testing.shouldBeInstanceOf
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

const val TIMEOUT_MS = 300L

inline fun <reified T> MockWebServer.createService(): T = Retrofit.Builder()
    .baseUrl(url("/"))
    .client(OkHttpClient.Builder().readTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS).build())
    .addConverterFactory(NetworkModule().provideJsonConverterFactory())
    .build()
    .create(T::class.java)

fun MockWebServer.enqueueJson(body: String, code: Int = 200) {
    enqueue(MockResponse().setResponseCode(code).setBody(body))
}

/** Asserts the flow emitted Loading then Error, and returns the error's exception. */
inline fun <reified E : Throwable> List<Resource<*>>.shouldBeLoadingThenError(): E {
    size shouldBe 2
    first() shouldBe Resource.Loading
    return last().shouldBeInstanceOf<Resource.Error>().exception.shouldBeInstanceOf<E>()
}

/** TMDB error body, e.g. invalid api key or unknown movie id. */
fun tmdbError(statusCode: Int, message: String) =
    """{"success":false,"status_code":$statusCode,"status_message":"$message"}"""
