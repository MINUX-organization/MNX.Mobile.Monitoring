package com.minux.monitoring.feature.flightsheets.impl.presentation.mapper

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.ConfigurationMode

internal fun ConfigurationMode.toMessage(): String = when (this) {
    is ConfigurationMode.Create -> "Create flight sheet"
    is ConfigurationMode.Edit -> "Edit flight sheet"
}