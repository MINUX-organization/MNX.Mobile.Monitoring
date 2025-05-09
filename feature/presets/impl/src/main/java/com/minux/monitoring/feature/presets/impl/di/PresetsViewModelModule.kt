package com.minux.monitoring.feature.presets.impl.di

import androidx.lifecycle.ViewModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.PresetConfigurationViewModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.PresetsViewModel
import com.minux.monitoring.injector.viewmodel.ViewModelFactoryModule
import com.minux.monitoring.injector.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal interface PresetsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PresetsViewModel::class)
    fun bindPresetsViewModel(presetsViewModel: PresetsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PresetConfigurationViewModel::class)
    fun bindPresetConfigurationViewModel(
        presetConfigurationViewModel: PresetConfigurationViewModel
    ): ViewModel
}