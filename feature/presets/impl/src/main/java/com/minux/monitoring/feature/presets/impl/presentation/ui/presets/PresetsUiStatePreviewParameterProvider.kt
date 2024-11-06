package com.minux.monitoring.feature.presets.impl.presentation.ui.presets

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.DevicePresetItemPreviewParameterProvider

internal class PresetsUiStatePreviewParameterProvider : PreviewParameterProvider<PresetsUiState> {
    override val values: Sequence<PresetsUiState> = sequenceOf(
        PresetsUiState(
            presets = DevicePresetItemPreviewParameterProvider().values.toList()
        )
    )
}