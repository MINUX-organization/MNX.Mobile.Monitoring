package com.minux.monitoring.feature.monitoring.impl.di

import androidx.lifecycle.ViewModel
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.MonitoringViewModel
import com.minux.monitoring.injector.viewmodel.ViewModelFactoryModule
import com.minux.monitoring.injector.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal interface MonitoringViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MonitoringViewModel::class)
    fun bindMonitoringViewModel(monitoringViewModel: MonitoringViewModel): ViewModel
}