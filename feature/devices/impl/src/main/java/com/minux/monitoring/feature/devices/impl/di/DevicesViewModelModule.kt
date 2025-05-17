package com.minux.monitoring.feature.devices.impl.di

import androidx.lifecycle.ViewModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.CpusViewModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.GpusViewModel
import com.minux.monitoring.injector.viewmodel.ViewModelFactoryModule
import com.minux.monitoring.injector.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal interface DevicesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CpusViewModel::class)
    fun bindCpusViewModel(cpusViewModel: CpusViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GpusViewModel::class)
    fun bindGpusViewModel(gpusViewModel: GpusViewModel): ViewModel
}