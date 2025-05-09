package com.minux.monitoring.feature.presets.impl.presentation.ui.apply.model

internal sealed interface PresetApplyAction {
    data object OpenPreviousScreen : PresetApplyAction

    data object ShowApplyDevicesFailedSnackBar : PresetApplyAction
}