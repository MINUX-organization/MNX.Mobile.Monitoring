package com.minux.monitoring.feature.presets.impl.presentation.model

internal data class GpuOtherParametersModel(
    val powerLimit: GpuTuningParameterModel?,
    val fanSpeed: GpuTuningParameterModel?
)