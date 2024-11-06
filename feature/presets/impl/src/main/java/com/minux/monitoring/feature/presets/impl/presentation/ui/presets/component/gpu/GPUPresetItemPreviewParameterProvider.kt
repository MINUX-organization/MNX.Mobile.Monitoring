package com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.gpu

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUOtherParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUOverclockParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUPresetItemModel

internal class GPUPresetItemPreviewParameterProvider : PreviewParameterProvider<GPUPresetItemModel> {
    override val values: Sequence<GPUPresetItemModel> = sequenceOf(
        GPUPresetItemModel(
            presetName = "Preset #1",
            deviceName = "Nvidia RTX 3070",
            memoryModel = "Samsung GDDR6",
            parameters = GPUParametersModel(
                core = GPUOverclockParametersModel(
                    clockLock = 1800f,
                    clockOffset = 900f,
                    clockRange = 800f..3000f,
                    voltageLock = 20f,
                    voltageOffset = 20f,
                    voltageRange = -100f..175f,
                ),
                memory = GPUOverclockParametersModel(
                    clockLock = 200f,
                    clockOffset = 200f,
                    clockRange = 0f..500f,
                    voltageLock = 20f,
                    voltageOffset = 20f,
                    voltageRange = -100f..175f
                ),
                other = GPUOtherParametersModel(
                    powerLimit = 250f,
                    powerLimitRange = 150f..300f,
                    fanSpeed = 65f,
                    fanSpeedRange = 30f..100f,
                    criticalTemperature = 50f,
                    criticalTemperatureRange = 0f..110f
                )
            )
        )
    )
}