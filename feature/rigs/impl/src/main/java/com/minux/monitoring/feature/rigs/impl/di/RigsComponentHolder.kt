package com.minux.monitoring.feature.rigs.impl.di

import com.minux.monitoring.feature.rigs.api.di.RigsFeatureApi
import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object RigsComponentHolder : ComponentHolder<RigsFeatureApi, RigsDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            RigsFeatureApi,
            RigsDependencies,
            RigsComponent> { monitoringDependencies ->
                RigsComponent.get(dependencies = monitoringDependencies)
            }

    override var dependencyProvider: (() -> RigsDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): RigsFeatureApi = componentHolderDelegate.fetchApi()

    internal fun fetchComponent(): RigsComponent = componentHolderDelegate.fetchComponent()
}