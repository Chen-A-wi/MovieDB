package com.awilab.network.di

import com.awilab.network.common.XLogInterceptor
import com.awilab.network.service.SearchService
import com.elvishew.xlog.XLog
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.ConnectionPool
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

internal const val BASE_URL = "https://api.themoviedb.org/3/"

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    private fun <T> Retrofit.createService(clazz: Class<T>): T {
        return this.create(clazz)
    }

    @Provides
    @Singleton
    fun provideJsonConverterFactory(): Converter.Factory {
        val jsonBuilder = Json {
            ignoreUnknownKeys = true // skip unknown json key
            coerceInputValues = true // null default
            prettyPrint = true // format
            encodeDefaults = true //序列化
            allowSpecialFloatingPointValues = true // allow special float value
        }

        return jsonBuilder.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        jasonConverterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jasonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            XLog.disableBorder()
                .disableStackTrace()
                .disableThreadInfo()
                .i(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(XLogInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            .protocols(listOf(Protocol.HTTP_1_1))
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchService(
        retrofit: Retrofit,
    ): SearchService {
        return retrofit.createService(SearchService::class.java)
    }
}