package com.minux.monitoring.feature.monitoring.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.feature.monitoring.api.MonitoringFeatureMediator
import com.minux.monitoring.feature.monitoring.impl.data.RigRepository
import com.minux.monitoring.feature.monitoring.impl.data.RigRepositoryImpl
import com.minux.monitoring.feature.monitoring.impl.data.datasource.RigControlApiService
import com.minux.monitoring.feature.monitoring.impl.presentation.navigation.MonitoringFeatureMediatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class MonitoringModule {

    @Provides
    @Singleton
    fun provideMonitoringFeatureMediator(): MonitoringFeatureMediator =
        MonitoringFeatureMediatorImpl()

    @Provides
    @Singleton
    fun provideRigRepository(httpClient: HttpClient): RigRepository {
        return with(httpClient.getApiClient()) {
            RigRepositoryImpl(rigControlApiService = create(RigControlApiService::class.java))
        }
    }
}