package com.minux.monitoring.feature.flightsheets.impl.di

import androidx.lifecycle.ViewModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.FlightSheetApplyViewModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.FlightSheetConfigurationViewModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.FlightSheetsViewModel
import com.minux.monitoring.injector.viewmodel.ViewModelFactoryModule
import com.minux.monitoring.injector.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal interface FlightSheetsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FlightSheetsViewModel::class)
    fun bindFlightSheetsViewModel(flightSheetsViewModel: FlightSheetsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlightSheetConfigurationViewModel::class)
    fun bindFlightSheetConfigurationViewModel(
        flightSheetConfigurationViewModel: FlightSheetConfigurationViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlightSheetApplyViewModel::class)
    fun bindFlightSheetApplyViewModel(flightSheetApplyViewModel: FlightSheetApplyViewModel): ViewModel
}