package com.minux.monitoring.core.network.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.api.WsClient
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.core.network.impl.HttpClientImpl
import com.minux.monitoring.core.network.impl.WsClientImpl
import com.minux.monitoring.core.network.impl.retrofit.AuthInterceptor
import com.minux.monitoring.core.network.impl.session.SessionManagerImpl
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideWsClient(sessionManager: SessionManager): WsClient =
        WsClientImpl(sessionManager = sessionManager as SessionManagerImpl)

    @Provides
    @Singleton
    fun provideHttpClient(okHttpClient: OkHttpClient): HttpClient =
        HttpClientImpl(okHttpClient = okHttpClient)

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor = httpLoggingInterceptor)
            .addInterceptor(interceptor = authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(lazySessionManager: Lazy<SessionManager>): AuthInterceptor =
        AuthInterceptor(lazySessionManager = lazySessionManager)
}