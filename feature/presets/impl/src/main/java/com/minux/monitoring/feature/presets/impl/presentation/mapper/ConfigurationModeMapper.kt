package com.minux.monitoring.feature.presets.impl.presentation.mapper

import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode

internal fun ConfigurationMode.toMessage(): String = when (this) {
    is ConfigurationMode.Create -> "Create preset"
    is ConfigurationMode.Edit -> "Edit preset"
    is ConfigurationMode.Overclock -> "Overclock"
}