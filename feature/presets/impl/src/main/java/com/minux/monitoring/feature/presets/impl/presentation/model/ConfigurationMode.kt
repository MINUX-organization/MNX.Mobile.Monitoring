package com.minux.monitoring.feature.presets.impl.presentation.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface ConfigurationMode {
    @Serializable
    data object Create : ConfigurationMode

    @Serializable
    class Edit(val presetId: String, val deviceName: String) : ConfigurationMode

    @Serializable
    class Overclock(val deviceId: String, val deviceName: String) : ConfigurationMode
}