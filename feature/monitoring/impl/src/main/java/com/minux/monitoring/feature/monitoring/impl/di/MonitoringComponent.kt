package com.minux.monitoring.feature.monitoring.impl.di

import androidx.lifecycle.ViewModelProvider
import com.minux.monitoring.feature.monitoring.api.di.MonitoringFeatureApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [MonitoringDependencies::class],
    modules = [MonitoringModule::class, MonitoringViewModelModule::class]
)
@Singleton
internal interface MonitoringComponent : MonitoringFeatureApi {

    val viewModelFactory: ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: MonitoringDependencies): MonitoringComponent
    }

    companion object {
        fun get(dependencies: MonitoringDependencies): MonitoringComponent {
            return DaggerMonitoringComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}