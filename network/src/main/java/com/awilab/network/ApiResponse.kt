package com.awilab.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface ApiResponse<out T> {
    data object Loading : ApiResponse<Nothing>
    data class Success<T>(val data: T) : ApiResponse<T>
    data class Error(val exception: Throwable) : ApiResponse<Nothing>
}

fun <T> Flow<T>.asApiResponse(): Flow<ApiResponse<T>> {
    return this.map<T, ApiResponse<T>> { data ->
        ApiResponse.Success(data = data)
    }.onStart {
        emit(ApiResponse.Loading)
    }.catch { exception ->
        emit(ApiResponse.Error(exception = exception))
    }
}