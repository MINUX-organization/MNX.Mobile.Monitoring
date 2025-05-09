package com.minux.monitoring.feature.presets.impl.data.model.device

import kotlinx.serialization.Serializable

@Serializable
internal class GpuTuningRestrictionDto(
    val core: GpuTuningParameterDto?,
    val memory: GpuTuningParameterDto?
)