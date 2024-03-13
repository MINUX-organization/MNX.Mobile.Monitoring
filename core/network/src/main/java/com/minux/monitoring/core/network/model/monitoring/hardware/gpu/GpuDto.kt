package com.minux.monitoring.core.network.model.monitoring.hardware.gpu

import com.minux.monitoring.core.network.model.monitoring.hardware.gpu.GpuInformationDto
import com.minux.monitoring.core.network.model.monitoring.hardware.gpu.GpuClocksDto

data class GpuDto(
    val index: Int,
    val bus: String,
    val name: String,
    val information: GpuInformationDto,
    val parameters: GpuClocksDto,
    val overclocking: GpuClocksDto
)
