package com.minux.monitoring.feature.presets.impl.data.model.device

import kotlinx.serialization.Serializable

@Serializable
internal class GpuTuningParameterDto(
    val lock: DeviceRestrictionDto?,
    val offset: DeviceRestrictionDto?
)