package com.minux.monitoring.feature.presets.impl.data.model.device

import kotlinx.serialization.Serializable

@Serializable
internal class GpuTemperatureRestrictionDto(
    val core: DeviceRestrictionDto?,
    val memory: DeviceRestrictionDto?
)