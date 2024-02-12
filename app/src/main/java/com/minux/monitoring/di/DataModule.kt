package com.minux.monitoring.di

import com.minux.monitoring.core.data.repository.WorkerRepository
import com.minux.monitoring.core.data.source.WorkerLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideWorkerRepository(workerLocalDataSource: WorkerLocalDataSource): WorkerRepository {
        return WorkerRepository(workerLocalDataSource = workerLocalDataSource)
    }
}