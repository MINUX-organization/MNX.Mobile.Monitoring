package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model.PresetConfigurationUiState
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.PresetItemPreviewParameterProvider

internal class PresetConfigurationUiStatePreviewParameterProvider : PreviewParameterProvider<PresetConfigurationUiState> {
    private val gpuPresetItems = PresetItemPreviewParameterProvider().values.toList()

    override val values: Sequence<PresetConfigurationUiState> = sequenceOf(
        PresetConfigurationUiState(
            devices = listOf("Device 1", "Device 2"),
            currentPreset = gpuPresetItems.last()
        ),
        PresetConfigurationUiState(
            devicesIsLoading = false,
            devices = listOf("Device 1", "Device 2"),
            currentPreset = gpuPresetItems.first().copy(
                parametersIsLoading = false
            )
        ),
        PresetConfigurationUiState(
            devicesIsLoading = false,
            devices = emptyList(),
            currentPreset = gpuPresetItems.last().copy(
                parametersIsLoading = false
            )
        )
    )
}