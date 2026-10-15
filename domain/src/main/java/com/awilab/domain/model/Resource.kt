package com.awilab.domain.model

sealed interface Resource<out T> {
    data object Loading : Resource<Nothing>
    data class Success<T>(val data: T) : Resource<T>
    data class Error(val exception: Throwable) : Resource<Nothing>
}
