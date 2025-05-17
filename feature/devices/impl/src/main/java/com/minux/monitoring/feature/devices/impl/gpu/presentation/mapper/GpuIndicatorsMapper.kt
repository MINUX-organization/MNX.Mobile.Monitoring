package com.minux.monitoring.feature.devices.impl.gpu.presentation.mapper

import com.minux.monitoring.feature.devices.impl.gpu.data.model.GpuIndicatorsDto
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuIndicatorsModel

internal fun GpuIndicatorsDto?.toGpuIndicatorsModel(): GpuIndicatorsModel {
    return this?.run {
        GpuIndicatorsModel(
            memoryTemperature = memoryTemperature,
            coreTemperature = coreTemperature,
            fanSpeed = fanSpeed,
            power = power
        )
    } ?: GpuIndicatorsModel()
}