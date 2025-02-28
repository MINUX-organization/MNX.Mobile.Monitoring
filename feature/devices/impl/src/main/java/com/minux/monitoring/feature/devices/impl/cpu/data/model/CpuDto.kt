package com.minux.monitoring.feature.devices.impl.cpu.data.model

import com.minux.monitoring.feature.devices.impl.common.data.model.DevicePci
import com.minux.monitoring.feature.devices.impl.cpu.data.model.info.CpuInfoDto
import kotlinx.serialization.Serializable

@Serializable
internal class CpuDto(
    val id: String,
    val pci: DevicePci?,
    val information: CpuInfoDto?,
    val rigName: String?,
    val flightSheetName: String?,
    val minerName: String?
)