package com.minux.monitoring.feature.presets.impl.presentation.model

internal class GPUOverclockParametersModel(
    val clockLock: Float,
    val clockOffset: Float,
    val clockRange: ClosedFloatingPointRange<Float>,
    val voltageLock: Float,
    val voltageOffset: Float,
    val voltageRange: ClosedFloatingPointRange<Float>
)