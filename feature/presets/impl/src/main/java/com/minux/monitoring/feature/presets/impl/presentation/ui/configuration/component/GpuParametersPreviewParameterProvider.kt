package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuOtherParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuTuningParameterModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuTuningParametersModel

internal class GpuParametersPreviewParameterProvider : PreviewParameterProvider<DeviceParametersModel.GpuParametersModel> {
    private val tuningParameter = GpuTuningParameterModel(
        value = 123f,
        default = 213f,
        rangeRestriction = -41f..578f,
        isWritable = true
    )

    override val values: Sequence<DeviceParametersModel.GpuParametersModel> = sequenceOf(
        DeviceParametersModel.GpuParametersModel(
            clocking = GpuTuningParametersModel(
                coreLock = tuningParameter,
                coreOffset = tuningParameter,
                memoryLock = tuningParameter,
                memoryOffset = tuningParameter
            ),
            voltage = GpuTuningParametersModel(
                coreLock = tuningParameter,
                coreOffset = null,
                memoryLock = tuningParameter,
                memoryOffset = null
            ),
            other = GpuOtherParametersModel(
                powerLimit = tuningParameter,
                fanSpeed = tuningParameter
            )
        )
    )
}