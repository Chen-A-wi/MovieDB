package com.awilab.common.coroutine

import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class CommonCoroutineScope @Inject constructor(
    private val dispatcher: CommonDispatcherProvider,
) {
    fun io(): CoroutineScope {
        return CoroutineScope(dispatcher.io)
    }

    fun default(): CoroutineScope {
        return CoroutineScope(dispatcher.default)
    }

    fun main(): CoroutineScope {
        return CoroutineScope(dispatcher.main)
    }

    fun unconfined(): CoroutineScope {
        return CoroutineScope(dispatcher.unconfined)
    }
}