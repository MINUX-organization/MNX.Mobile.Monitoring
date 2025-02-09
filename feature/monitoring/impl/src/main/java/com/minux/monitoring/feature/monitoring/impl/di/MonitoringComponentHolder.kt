package com.minux.monitoring.feature.monitoring.impl.di

import com.minux.monitoring.feature.monitoring.api.di.MonitoringFeatureApi
import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object MonitoringComponentHolder : ComponentHolder<MonitoringFeatureApi, MonitoringDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            MonitoringFeatureApi,
            MonitoringDependencies,
            MonitoringComponent> { monitoringDependencies ->
                MonitoringComponent.get(dependencies = monitoringDependencies)
            }

    override var dependencyProvider: (() -> MonitoringDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): MonitoringFeatureApi = componentHolderDelegate.fetchApi()

    internal fun fetchComponent(): MonitoringComponent = componentHolderDelegate.fetchComponent()
}