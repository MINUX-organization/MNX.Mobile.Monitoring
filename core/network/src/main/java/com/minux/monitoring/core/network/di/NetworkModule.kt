package com.minux.monitoring.core.network.di

import android.content.Context
import com.minux.monitoring.core.network.NetworkScanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkScanService(@ApplicationContext appContext: Context): NetworkScanService {
        return NetworkScanService(context = appContext)
    }
}