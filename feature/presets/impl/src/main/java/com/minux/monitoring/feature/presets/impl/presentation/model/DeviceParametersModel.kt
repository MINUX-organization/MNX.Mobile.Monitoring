package com.minux.monitoring.feature.presets.impl.presentation.model

internal sealed interface DeviceParametersModel {
    data class GpuParametersModel(
        val clocking: GpuTuningParametersModel,
        val voltage: GpuTuningParametersModel,
        val other: GpuOtherParametersModel
    ) : DeviceParametersModel

    data object CpuParametersModel : DeviceParametersModel
}