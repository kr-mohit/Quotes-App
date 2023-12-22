package com.idonnoe.quotesapp.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://quotable.io/"

    fun getInstance() :Retrofit {

        //This is for logging the url retrofit will hit
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(LoggingInterceptor())

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}