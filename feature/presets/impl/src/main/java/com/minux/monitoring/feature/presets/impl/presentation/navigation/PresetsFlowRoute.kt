package com.minux.monitoring.feature.presets.impl.presentation.navigation

import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode
import kotlinx.serialization.Serializable

internal sealed interface PresetsFlowRoute {
    @Serializable
    data object Presets : PresetsFlowRoute

    @Serializable
    class PresetApply(val id: String, val name: String) : PresetsFlowRoute

    @Serializable
    class PresetConfiguration(val mode: ConfigurationMode) : PresetsFlowRoute
}