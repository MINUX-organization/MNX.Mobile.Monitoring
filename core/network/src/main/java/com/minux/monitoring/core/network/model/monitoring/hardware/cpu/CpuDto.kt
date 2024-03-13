package com.minux.monitoring.core.network.model.monitoring.hardware.cpu

data class CpuDto(
    val name: String,
    val architecture: String,
    val coresCount: Int,
    val serialNumber: String,
    val threadsCount: Int,
    val threadsPerSocketCount: Int,
    val clocking: CpuClocksDto
)
