package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.model

import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode

internal sealed interface PresetsAction {
    class OpenPresetApplyScreen(
        val presetId: String,
        val presetName: String
    ) : PresetsAction

    class OpenPresetConfigurationScreen(val mode: ConfigurationMode) : PresetsAction

    data object ShowRemovePresetFailedSnackBar : PresetsAction
}