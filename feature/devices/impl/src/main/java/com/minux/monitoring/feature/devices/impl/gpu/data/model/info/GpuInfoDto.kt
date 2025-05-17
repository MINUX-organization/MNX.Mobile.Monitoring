package com.minux.monitoring.feature.devices.impl.gpu.data.model.info

import kotlinx.serialization.Serializable

@Serializable
internal class GpuInfoDto(
    val manufacturer: String?,
    val model: String?,
    val name: String?,
    val serialNumber: String?,
    val vendor: String?,
    val biosVersion: String?,
    val technology: GpuTechnologyDto?,
    val memory: GpuMemoryDto?
)