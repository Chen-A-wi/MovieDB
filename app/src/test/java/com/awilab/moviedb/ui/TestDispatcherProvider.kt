package com.awilab.moviedb.ui

import com.awilab.common.coroutine.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher

class TestDispatcherProvider(dispatcher: CoroutineDispatcher) : DispatcherProvider {
    override val io = dispatcher
    override val main = dispatcher
    override val default = dispatcher
    override val unconfined = dispatcher
}
