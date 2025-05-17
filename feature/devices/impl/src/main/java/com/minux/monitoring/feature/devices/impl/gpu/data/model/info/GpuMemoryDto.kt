package com.minux.monitoring.feature.devices.impl.gpu.data.model.info

import kotlinx.serialization.Serializable

@Serializable
internal class GpuMemoryDto(
    val total: Int,
    val type: String?,
    val vendor: String?
)