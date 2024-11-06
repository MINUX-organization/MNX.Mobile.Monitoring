package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.model.SaveAsPresetModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.gpu.GPUPresetItemPreviewParameterProvider

internal class PresetConfigurationUiStatePreviewParameterProvider : PreviewParameterProvider<PresetConfigurationUiState> {
    private val gpuPresetItems = GPUPresetItemPreviewParameterProvider().values.toList()

    override val values: Sequence<PresetConfigurationUiState> = sequenceOf(
        PresetConfigurationUiState(
            devices = gpuPresetItems.map { it.deviceName },
            currentPreset = gpuPresetItems.first(),
            presets = gpuPresetItems
        )
    )
}