package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration

import com.minux.monitoring.feature.presets.impl.presentation.model.GPUPresetItemModel

internal class PresetConfigurationUiState(
    val devices: List<String>,
    val currentPreset: GPUPresetItemModel,
    val presets: List<GPUPresetItemModel>
)