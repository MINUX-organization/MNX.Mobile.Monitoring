package com.minux.monitoring.feature.devices.impl.common.data.model

import com.minux.monitoring.feature.devices.impl.cpu.data.model.CpuIndicatorsDto
import com.minux.monitoring.feature.devices.impl.gpu.data.model.GpuIndicatorsDto

internal class DeviceIndicatorsDto(
    val cpusIndicators: List<CpuIndicatorsDto>?,
    val gpusIndicators: List<GpuIndicatorsDto>?
)