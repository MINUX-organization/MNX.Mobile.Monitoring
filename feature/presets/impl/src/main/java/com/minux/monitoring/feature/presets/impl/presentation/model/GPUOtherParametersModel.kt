package com.minux.monitoring.feature.presets.impl.presentation.model

internal class GPUOtherParametersModel(
    val powerLimit: Float,
    val powerLimitRange: ClosedFloatingPointRange<Float>,
    val fanSpeed: Float,
    val fanSpeedRange: ClosedFloatingPointRange<Float>,
    val criticalTemperature: Float,
    val criticalTemperatureRange: ClosedFloatingPointRange<Float>
)