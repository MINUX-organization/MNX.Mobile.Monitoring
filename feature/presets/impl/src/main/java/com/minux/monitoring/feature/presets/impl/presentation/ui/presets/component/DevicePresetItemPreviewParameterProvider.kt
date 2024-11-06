package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetItemModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.gpu.GPUPresetItemPreviewParameterProvider

internal class DevicePresetItemPreviewParameterProvider : PreviewParameterProvider<DevicePresetItemModel> {

    override val values: Sequence<DevicePresetItemModel> = sequenceOf(
        DevicePresetItemModel(
            name = "Nvidia RTX 3070",
            presets = GPUPresetItemPreviewParameterProvider().values.toList()
        ),
        DevicePresetItemModel(
            name = "Nvidia RTX 3080 Ti",
            presets = GPUPresetItemPreviewParameterProvider().values.toList()
        )
    )
}