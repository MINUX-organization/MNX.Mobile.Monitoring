package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.model.PresetsUiState

internal class PresetsUiStatePreviewParameterProvider : PreviewParameterProvider<PresetsUiState> {
    override val values: Sequence<PresetsUiState> = sequenceOf(
        PresetsUiState(
            presetGroupsIsLoading = false,
            presetGroups = DevicePresetItemPreviewParameterProvider().values.toList(),
            filteredPresetGroups = DevicePresetItemPreviewParameterProvider().values.toList()
        ),
        PresetsUiState(
            presetGroupsIsLoading = false,
            presetGroups = null,
            filteredPresetGroups = null
        ),
        PresetsUiState(
            presetGroups = DevicePresetItemPreviewParameterProvider().values.toList()
        )
    )
}