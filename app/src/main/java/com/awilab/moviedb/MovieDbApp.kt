package com.awilab.moviedb

import android.app.Application
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieDbApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initLog()
    }

    private fun initLog() {
        XLog.init(
            LogConfiguration.Builder()
                .logLevel(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
                .build()
        )
    }
}