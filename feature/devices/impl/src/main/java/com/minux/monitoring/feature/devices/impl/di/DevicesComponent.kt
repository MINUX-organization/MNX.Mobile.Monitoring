package com.minux.monitoring.feature.devices.impl.di

import androidx.lifecycle.ViewModelProvider
import com.minux.monitoring.feature.devices.api.di.DevicesFeatureApi
import com.minux.monitoring.feature.presets.api.PresetsFeatureMediator
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [DevicesDependencies::class],
    modules = [DevicesModule::class, DevicesViewModelModule::class]
)
@Singleton
internal interface DevicesComponent : DevicesFeatureApi {

    val viewModelFactory: ViewModelProvider.Factory
    val presetsFeatureMediator: PresetsFeatureMediator

    @Component.Factory
    interface Factory {
        fun create(dependencies: DevicesDependencies): DevicesComponent
    }

    companion object {
        fun get(dependencies: DevicesDependencies): DevicesComponent {
            return DaggerDevicesComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}