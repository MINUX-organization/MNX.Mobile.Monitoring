package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model

import com.minux.monitoring.feature.presets.impl.presentation.model.PresetItemModel

internal data class PresetConfigurationUiState(
    val devicesIsLoading: Boolean = true,
    val devices: List<String> = emptyList(),
    val currentPreset: PresetItemModel = PresetItemModel()
)