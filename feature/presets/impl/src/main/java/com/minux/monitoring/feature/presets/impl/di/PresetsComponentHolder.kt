package com.minux.monitoring.feature.presets.impl.di

import com.minux.monitoring.feature.presets.api.di.PresetsFeatureApi
import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object PresetsComponentHolder : ComponentHolder<PresetsFeatureApi, PresetsDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            PresetsFeatureApi, PresetsDependencies, PresetsComponent> { presetsDependencies ->
                PresetsComponent.get(dependencies = presetsDependencies)
            }

    override var dependencyProvider: (() -> PresetsDependencies)?
        by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): PresetsFeatureApi = componentHolderDelegate.fetchApi()

    internal fun fetchComponent(): PresetsComponent = componentHolderDelegate.fetchComponent()
}