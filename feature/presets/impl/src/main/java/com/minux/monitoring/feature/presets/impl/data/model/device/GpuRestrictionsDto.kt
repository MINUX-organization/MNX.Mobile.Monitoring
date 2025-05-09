package com.minux.monitoring.feature.presets.impl.data.model.device

import kotlinx.serialization.Serializable

internal sealed interface DeviceRestrictionsDto {
    @Serializable
    data object CpuRestrictionsDto : DeviceRestrictionsDto

    @Serializable
    class GpuRestrictionsDto(
        val power: DeviceRestrictionDto?,
        val fanSpeed: DeviceRestrictionDto?,
        val temperature: GpuTemperatureRestrictionDto?,
        val voltage: GpuTuningRestrictionDto?,
        val clock: GpuTuningRestrictionDto?
    ) : DeviceRestrictionsDto
}