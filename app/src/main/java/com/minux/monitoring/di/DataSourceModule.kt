package com.minux.monitoring.di

import android.content.Context
import com.minux.monitoring.core.data.source.WorkerLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideWorkerLocalDataSource(@ApplicationContext context: Context): WorkerLocalDataSource {
        return WorkerLocalDataSource(context = context)
    }
}