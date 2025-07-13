package com.minux.monitoring.feature.flightsheets.impl.presentation.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface ConfigurationMode {
    @Serializable
    data object Create : ConfigurationMode

    @Serializable
    class Edit(val flightSheetId: String) : ConfigurationMode
}