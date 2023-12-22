package com.idonnoe.quotesapp.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LoggingInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val url = request.url.toString()

        Log.d("mohit", url)

        return chain.proceed(request)
    }
}