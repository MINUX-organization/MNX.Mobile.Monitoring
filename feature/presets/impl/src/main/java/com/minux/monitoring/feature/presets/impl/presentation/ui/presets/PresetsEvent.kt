package com.minux.monitoring.feature.presets.impl.presentation.ui.presets

internal sealed interface PresetsEvent {
    data object ChangePreset : PresetsEvent
}