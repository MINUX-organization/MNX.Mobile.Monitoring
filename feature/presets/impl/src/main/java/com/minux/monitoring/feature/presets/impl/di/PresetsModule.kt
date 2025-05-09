package com.minux.monitoring.feature.presets.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.feature.presets.api.PresetsFeatureMediator
import com.minux.monitoring.feature.presets.impl.data.datasource.DeviceApiService
import com.minux.monitoring.feature.presets.impl.data.datasource.PresetApiService
import com.minux.monitoring.feature.presets.impl.data.repository.DeviceRepository
import com.minux.monitoring.feature.presets.impl.data.repository.DeviceRepositoryImpl
import com.minux.monitoring.feature.presets.impl.data.repository.PresetRepository
import com.minux.monitoring.feature.presets.impl.data.repository.PresetRepositoryImpl
import com.minux.monitoring.feature.presets.impl.presentation.navigation.PresetsFeatureMediatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class PresetsModule {

    @Provides
    @Singleton
    fun providePresetsFeatureMediator(): PresetsFeatureMediator =
        PresetsFeatureMediatorImpl()

    @Provides
    @Singleton
    fun providePresetRepository(httpClient: HttpClient): PresetRepository {
        return with(httpClient.getApiClient()) {
            PresetRepositoryImpl(presetApiService = create(PresetApiService::class.java))
        }
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(httpClient: HttpClient): DeviceRepository {
        return with(httpClient.getApiClient()) {
            DeviceRepositoryImpl(deviceApiService = create(DeviceApiService::class.java))
        }
    }
}