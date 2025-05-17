package com.minux.monitoring.feature.devices.impl.cpu.presentation.mapper

import com.minux.monitoring.feature.devices.impl.cpu.data.model.CpuIndicatorsDto
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuIndicatorsModel

internal fun CpuIndicatorsDto?.toCpuIndicatorsModel(): CpuIndicatorsModel {
    return this?.run {
        CpuIndicatorsModel(
            temperature = temperature,
            fanSpeed = fanSpeed,
            power = power
        )
    } ?: CpuIndicatorsModel()
}