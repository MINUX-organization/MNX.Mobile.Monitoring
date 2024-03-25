package com.minux.monitoring.core.network.model.monitoring.hardware.gpu

data class GpuDto(
    val index: Int,
    val bus: String,
    val name: String,
    val information: GpuInformationDto,
    val parameters: GpuClocksDto,
    val overclocking: GpuClocksDto
)
