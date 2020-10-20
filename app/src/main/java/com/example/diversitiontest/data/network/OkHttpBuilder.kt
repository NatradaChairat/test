package com.example.diversitiontest.data.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpBuilder(val context: Context, vararg val interceptors: Interceptor) {

    private val baseConfig = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)

    fun build(): OkHttpClient {
        interceptors.forEach {
            baseConfig.addInterceptor(it)
        }
        return baseConfig.build()
    }

}