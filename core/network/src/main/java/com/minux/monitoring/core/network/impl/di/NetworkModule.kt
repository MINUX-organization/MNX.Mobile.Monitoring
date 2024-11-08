package com.minux.monitoring.core.network.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.api.WsClient
import com.minux.monitoring.core.network.impl.HttpClientImpl
import com.minux.monitoring.core.network.impl.WsClientImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
internal interface NetworkModule {
    @Binds
    @Singleton
    fun bindsHttpClient(httpClientImpl: HttpClientImpl): HttpClient

    @Binds
    @Singleton
    fun bindsWsClient(wsClientImpl: WsClientImpl): WsClient

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}