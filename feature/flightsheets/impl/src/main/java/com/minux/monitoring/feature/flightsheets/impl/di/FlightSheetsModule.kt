package com.minux.monitoring.feature.flightsheets.impl.di

import com.minux.monitoring.core.network.api.BackendApi
import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.feature.flightsheets.api.FlightSheetsFeatureMediator
import com.minux.monitoring.feature.flightsheets.impl.data.datasource.FlightSheetApiService
import com.minux.monitoring.feature.flightsheets.impl.data.datasource.MinerApiService
import com.minux.monitoring.feature.flightsheets.impl.data.repository.FlightSheetRepository
import com.minux.monitoring.feature.flightsheets.impl.data.repository.FlightSheetRepositoryImpl
import com.minux.monitoring.feature.flightsheets.impl.data.repository.MinerRepository
import com.minux.monitoring.feature.flightsheets.impl.data.repository.MinerRepositoryImpl
import com.minux.monitoring.feature.flightsheets.impl.presentation.navigation.FlightSheetsFeatureMediatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class FlightSheetsModule {

    @Provides
    @Singleton
    fun provideFlightSheetsFeatureMediator(): FlightSheetsFeatureMediator =
        FlightSheetsFeatureMediatorImpl()

    @Provides
    @Singleton
    fun provideFlightSheetRepository(httpClient: HttpClient): FlightSheetRepository {
        return with(httpClient.getApiClient(BackendApi.Monitoring)) {
            FlightSheetRepositoryImpl(
                flightSheetApiService = create(FlightSheetApiService::class.java)
            )
        }
    }

    @Provides
    @Singleton
    fun provideMinerRepository(httpClient: HttpClient): MinerRepository {
        return with(httpClient.getApiClient(BackendApi.Monitoring)) {
            MinerRepositoryImpl(minerApiService = create(MinerApiService::class.java))
        }
    }
}