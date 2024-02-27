package com.minux.monitoring.core.network.di

import android.content.Context
import com.minux.monitoring.core.network.MNXApiService
import com.minux.monitoring.core.network.MNXAsyncApiService
import com.minux.monitoring.core.network.async.MNXAsyncApiServiceImpl
import com.minux.monitoring.core.network.service.NetworkScanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    private const val baseUrl = "https://something.com/api/"

    @Provides
    @Singleton
    fun provideMNXApiService(okHttpClient: OkHttpClient): MNXApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(MNXApiService::class.java)
    }

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

    @Provides
    @Singleton
    fun provideMNXAsyncApiService(): MNXAsyncApiService {
        return MNXAsyncApiServiceImpl()
    }

    @Provides
    @Singleton
    fun provideNetworkScanService(@ApplicationContext appContext: Context): NetworkScanService {
        return NetworkScanService(context = appContext)
    }
}