package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model

internal sealed interface CpusEvent {
    data object Filters : CpusEvent
}