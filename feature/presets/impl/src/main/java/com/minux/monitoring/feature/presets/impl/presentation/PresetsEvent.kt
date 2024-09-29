package com.minux.monitoring.feature.presets.impl.presentation

sealed interface PresetsEvent {
    data object CreatePreset : PresetsEvent
}