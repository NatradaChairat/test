package com.example.diversitiontest.di

import com.example.diversitiontest.data.MockInterceptor
import com.example.diversitiontest.data.network.OkHttpBuilder
import com.example.diversitiontest.data.network.RetrofitBuilder
import com.example.diversitiontest.data.repository.Repository
import com.example.diversitiontest.data.repository.RepositoryImpl
import com.example.diversitiontest.di.DataSourceProperties.SERVER_URL
import com.example.diversitiontest.service.CartService
import com.example.diversitiontest.service.ProductService
import com.example.diversitiontest.util.LiveDataCallAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

object DataSourceProperties {
    const val SERVER_URL = "SERVER_URL"
}

val serviceModule = module {
    single(named("mockInterceptor")) { MockInterceptor() }
    single(named("loggingInterceptor")) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single(named("okHttpMock")) { OkHttpBuilder(get(), get(named("mockInterceptor")), get(named("loggingInterceptor"))).build() }

    single<CallAdapter.Factory>(named("liveDataFactory")) { LiveDataCallAdapterFactory() }
    single<Converter.Factory>(named("gsonFactory")) { GsonConverterFactory.create() }

    single(named("retrofitMock")) { RetrofitBuilder(get(named("okHttpMock")), get(named("gsonFactory")), get(named("liveDataFactory"))) }
}

val networkModule = module {
    single<ProductService> { get<RetrofitBuilder>(named("retrofitMock")).build(getProperty(SERVER_URL)) }
    single<CartService> { get<RetrofitBuilder>(named("retrofitMock")).build(getProperty(SERVER_URL)) }
}

val repositoryModule = module {
    single<Repository> { RepositoryImpl(get(), get()) }
}