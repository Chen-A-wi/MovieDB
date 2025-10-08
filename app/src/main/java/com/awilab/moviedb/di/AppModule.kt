package com.awilab.moviedb.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Application Context
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext
}