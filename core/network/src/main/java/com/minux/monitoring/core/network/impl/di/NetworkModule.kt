package com.minux.monitoring.core.network.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.api.WsClient
import com.minux.monitoring.core.network.impl.HttpClientImpl
import com.minux.monitoring.core.network.impl.WsClientImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(okHttpClient: OkHttpClient): HttpClient =
        HttpClientImpl(okHttpClient = okHttpClient)

    @Provides
    @Singleton
    fun provideWsClient(): WsClient = WsClientImpl()

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