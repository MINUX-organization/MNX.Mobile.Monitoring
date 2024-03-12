package com.minux.monitoring.core.network.model.monitoring.hardware.gpu

data class GpuClocksDto(
    val coreClock: String,
    val coreClockOffset: String,
    val memoryClock: String,
    val memoryClockOffset: String,
    val voltage: String,
    val voltageOffset: String
)
