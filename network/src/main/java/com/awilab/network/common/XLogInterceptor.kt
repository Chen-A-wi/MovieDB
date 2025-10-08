package com.awilab.network.common

import com.elvishew.xlog.XLog
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class XLogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val responseBody = response.body
        val content = responseBody?.string().orEmpty()

        XLog.disableStackTrace().json(content) // JSON format

        // 重新包裝 response body，避免被消耗
        return response.newBuilder()
            .body(content.toResponseBody(responseBody?.contentType()))
            .build()
    }
}