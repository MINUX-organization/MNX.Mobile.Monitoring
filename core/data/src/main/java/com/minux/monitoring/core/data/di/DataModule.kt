package com.minux.monitoring.core.data.di

import com.minux.monitoring.core.data.repository.RigRepository
import com.minux.monitoring.core.data.source.RigLocalDataSource
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
    fun provideRigRepository(rigLocalDataSource: RigLocalDataSource): RigRepository {
        return RigRepository(rigLocalDataSource = rigLocalDataSource)
    }
}