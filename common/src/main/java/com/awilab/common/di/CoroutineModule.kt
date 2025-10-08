package com.awilab.common.di

import com.awilab.common.coroutine.CommonCoroutineScope
import com.awilab.common.coroutine.CommonDispatcherProvider
import com.awilab.common.coroutine.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return CommonDispatcherProvider()
    }

    @Provides
    @Singleton
    fun provideScopeProvider(dispatcherProvider: CommonDispatcherProvider): CommonCoroutineScope {
        return CommonCoroutineScope(dispatcherProvider)
    }

}