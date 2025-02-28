package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model

internal sealed interface GpusEvent {
    data object Filters : GpusEvent

    data object Settings : GpusEvent
}