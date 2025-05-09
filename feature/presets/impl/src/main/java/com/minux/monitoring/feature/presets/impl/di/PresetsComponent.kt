package com.minux.monitoring.feature.presets.impl.di

import androidx.lifecycle.ViewModelProvider
import com.minux.monitoring.feature.presets.api.di.PresetsFeatureApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [PresetsDependencies::class],
    modules = [PresetsModule::class, PresetsViewModelModule::class]
)
@Singleton
internal interface PresetsComponent : PresetsFeatureApi {

    val viewModelFactory: ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: PresetsDependencies): PresetsComponent
    }

    companion object {
        fun get(dependencies: PresetsDependencies): PresetsComponent {
            return DaggerPresetsComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}