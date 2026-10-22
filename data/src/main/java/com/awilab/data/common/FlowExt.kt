package com.awilab.data.common

import com.awilab.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.asResource(): Flow<Resource<T>> {
    return this.map<T, Resource<T>> { data ->
        Resource.Success(data = data)
    }.onStart {
        emit(Resource.Loading)
    }.catch { exception ->
        emit(Resource.Error(exception = exception))
    }
}
