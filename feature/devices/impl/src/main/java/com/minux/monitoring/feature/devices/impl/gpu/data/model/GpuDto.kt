package com.minux.monitoring.feature.devices.impl.gpu.data.model

import com.minux.monitoring.feature.devices.impl.common.data.model.DevicePci
import com.minux.monitoring.feature.devices.impl.gpu.data.model.info.GpuInfoDto
import kotlinx.serialization.Serializable

@Serializable
internal class GpuDto(
    val id: String,
    val pci: DevicePci?,
    val information: GpuInfoDto?,
    val rigName: String?,
    val driverVersion: String?,
    val flightSheetName: String?,
    val presetName: String?,
    val minerName: String?,
    val isOnline: Boolean
)