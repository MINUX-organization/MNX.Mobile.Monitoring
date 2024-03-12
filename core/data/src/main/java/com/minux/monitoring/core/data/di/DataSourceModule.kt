package com.minux.monitoring.core.data.di

import android.content.Context
import com.minux.monitoring.core.data.source.RigLocalDataSource
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
    fun provideRigLocalDataSource(@ApplicationContext context: Context): RigLocalDataSource {
        return RigLocalDataSource(context = context)
    }
}