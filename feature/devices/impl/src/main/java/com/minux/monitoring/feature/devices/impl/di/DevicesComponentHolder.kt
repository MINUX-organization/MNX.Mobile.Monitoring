package com.minux.monitoring.feature.devices.impl.di

import com.minux.monitoring.feature.devices.api.di.DevicesFeatureApi
import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object DevicesComponentHolder : ComponentHolder<DevicesFeatureApi, DevicesDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            DevicesFeatureApi, DevicesDependencies, DevicesComponent> { devicesDependencies ->
                DevicesComponent.get(dependencies = devicesDependencies)
            }

    override var dependencyProvider: (() -> DevicesDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): DevicesFeatureApi = componentHolderDelegate.fetchApi()

    internal fun fetchComponent(): DevicesComponent = componentHolderDelegate.fetchComponent()
}