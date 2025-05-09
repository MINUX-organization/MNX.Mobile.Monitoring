package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model

internal sealed interface PresetConfigurationAction {
    data object OpenPreviousScreen : PresetConfigurationAction

    data object ShowCreatePresetFailedSnackBar : PresetConfigurationAction

    data object ShowChangePresetFailedSnackBar : PresetConfigurationAction

    data object ShowApplyOverclockingFailedSnackBar : PresetConfigurationAction

    data object ShowSaveAsPresetFailedSnackBar : PresetConfigurationAction

    data object OpenSaveAsPresetDialog : PresetConfigurationAction

    data object CloseSaveAsPresetDialog : PresetConfigurationAction
}