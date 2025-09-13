package com.minux.monitoring.feature.rigs.impl.di

import com.minux.monitoring.core.network.api.BackendApi
import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.feature.rigs.api.RigsFeatureMediator
import com.minux.monitoring.feature.rigs.impl.data.RigRepository
import com.minux.monitoring.feature.rigs.impl.data.RigRepositoryImpl
import com.minux.monitoring.feature.rigs.impl.data.datasource.RigApiService
import com.minux.monitoring.feature.rigs.impl.data.datasource.RigLifecycleApiService
import com.minux.monitoring.feature.rigs.impl.presentation.navigation.RigsFeatureMediatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class RigsModule {

    @Provides
    @Singleton
    fun provideMonitoringFeatureMediator(): RigsFeatureMediator =
        RigsFeatureMediatorImpl()

    @Provides
    @Singleton
    fun provideRigRepository(httpClient: HttpClient): RigRepository {
        return with(httpClient.getApiClient(BackendApi.Monitoring)) {
            RigRepositoryImpl(
                rigApiService = create(RigApiService::class.java),
                rigLifecycleApiService = create(RigLifecycleApiService::class.java)
            )
        }
    }
}