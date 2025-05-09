package com.minux.monitoring.feature.presets.impl.presentation.model

internal data class GpuTuningParametersModel(
    val coreLock: GpuTuningParameterModel?,
    val coreOffset: GpuTuningParameterModel?,
    val memoryLock: GpuTuningParameterModel?,
    val memoryOffset: GpuTuningParameterModel?
)