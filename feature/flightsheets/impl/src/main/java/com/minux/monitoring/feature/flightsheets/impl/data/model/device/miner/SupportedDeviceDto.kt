package com.minux.monitoring.feature.flightsheets.impl.data.model.device.miner

import kotlinx.serialization.Serializable

@Serializable
internal enum class SupportedDeviceDto {
    None,
    NvidiaGpu,
    IntelGpu,
    IntelCpu,
    AmdGpu,
    AmdCpu
}