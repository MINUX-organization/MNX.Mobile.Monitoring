package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetInfoModel
import com.minux.monitoring.feature.presets.impl.presentation.model.PresetItemModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component.GpuParametersPreviewParameterProvider

internal class PresetItemPreviewParameterProvider : PreviewParameterProvider<PresetItemModel> {
    private val gpuParameters = GpuParametersPreviewParameterProvider().values.toList()

    private val presetItem = PresetItemModel(
        id = "0",
        info = DevicePresetInfoModel(
            presetName = "Preset #1",
            deviceName = "Nvidia RTX 3070"
        ),
        parameters = gpuParameters.first()
    )

    override val values: Sequence<PresetItemModel> = sequenceOf(
        presetItem,
        presetItem.copy(
            id = "1",
            info = DevicePresetInfoModel(),
            parameters = gpuParameters.first()
        ),
        presetItem.copy(
            id = "2",
            parameters = null
        )
    )
}