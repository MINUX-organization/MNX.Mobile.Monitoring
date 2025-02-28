package com.minux.monitoring.feature.devices.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.feature.devices.api.DevicesFeatureMediator
import com.minux.monitoring.feature.devices.impl.DevicesFeatureMediatorImpl
import com.minux.monitoring.feature.devices.impl.cpu.data.datasource.CpuApiService
import com.minux.monitoring.feature.devices.impl.cpu.data.repository.CpuRepository
import com.minux.monitoring.feature.devices.impl.cpu.data.repository.CpuRepositoryImpl
import com.minux.monitoring.feature.devices.impl.gpu.data.datasource.GpuApiService
import com.minux.monitoring.feature.devices.impl.gpu.data.repository.GpuRepository
import com.minux.monitoring.feature.devices.impl.gpu.data.repository.GpuRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DevicesModule {

    @Provides
    @Singleton
    fun provideDevicesFeatureMediator(): DevicesFeatureMediator =
        DevicesFeatureMediatorImpl()

    @Provides
    @Singleton
    fun provideCpuRepository(httpClient: HttpClient): CpuRepository {
        return with(httpClient.getApiClient()) {
            CpuRepositoryImpl(cpuApiService = create(CpuApiService::class.java))
        }
    }

    @Provides
    @Singleton
    fun provideGpuRepository(httpClient: HttpClient): GpuRepository {
        return with(httpClient.getApiClient()) {
            GpuRepositoryImpl(gpuApiService = create(GpuApiService::class.java))
        }
    }
}