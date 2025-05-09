package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.model

internal sealed interface PresetsEvent {
    data object FetchPresets : PresetsEvent

    class SearchQueryChanged(val searchQuery: String) : PresetsEvent

    data object CreatePreset : PresetsEvent

    class ApplyPreset(
        val presetId: String,
        val presetName: String
    ) : PresetsEvent

    class ChangePreset(
        val presetId: String,
        val deviceName: String
    ) : PresetsEvent

    class RemovePreset(val presetId: String) : PresetsEvent
}