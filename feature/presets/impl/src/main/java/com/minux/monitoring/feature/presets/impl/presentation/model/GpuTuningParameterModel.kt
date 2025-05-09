package com.minux.monitoring.feature.presets.impl.presentation.model

internal data class GpuTuningParameterModel(
    val value: Float,
    val default: Float?,
    val rangeRestriction: ClosedFloatingPointRange<Float>,
    val isWritable: Boolean
)