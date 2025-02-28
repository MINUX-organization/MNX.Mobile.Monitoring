package com.minux.monitoring.feature.devices.impl.gpu.data.model.info

import kotlinx.serialization.Serializable

@Serializable
internal class GpuTechnologyDto(
    val type: GpuTechnologyType,
    val version: String?
)