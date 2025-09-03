package com.minux.monitoring.feature.flightsheets.impl.di

import androidx.lifecycle.ViewModelProvider
import com.minux.monitoring.feature.flightsheets.api.di.FlightSheetsFeatureApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [FlightSheetsDependencies::class],
    modules = [FlightSheetsModule::class, FlightSheetsViewModelModule::class]
)
@Singleton
internal interface FlightSheetsComponent : FlightSheetsFeatureApi {

    val viewModelFactory: ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: FlightSheetsDependencies): FlightSheetsComponent
    }

    companion object {
        fun get(dependencies: FlightSheetsDependencies): FlightSheetsComponent {
            return DaggerFlightSheetsComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}