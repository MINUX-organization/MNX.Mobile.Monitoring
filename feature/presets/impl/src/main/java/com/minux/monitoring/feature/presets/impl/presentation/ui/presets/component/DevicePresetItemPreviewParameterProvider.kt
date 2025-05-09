package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetGroupItemModel

internal class DevicePresetItemPreviewParameterProvider : PreviewParameterProvider<DevicePresetGroupItemModel> {

    override val values: Sequence<DevicePresetGroupItemModel> = sequenceOf(
        DevicePresetGroupItemModel(
            name = "Nvidia RTX 3070",
            presets = PresetItemPreviewParameterProvider().values.toList()
        ),
        DevicePresetGroupItemModel(
            name = null,
            presets = PresetItemPreviewParameterProvider().values.toList()
        ),
        DevicePresetGroupItemModel(
            name = "Nvidia RTX 3080 Ti",
            presets = PresetItemPreviewParameterProvider().values.toList()
        )
    )
}