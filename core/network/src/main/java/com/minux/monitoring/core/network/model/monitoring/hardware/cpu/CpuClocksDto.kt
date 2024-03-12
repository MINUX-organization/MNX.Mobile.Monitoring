package com.minux.monitoring.core.network.model.monitoring.hardware.cpu

data class CpuClocksDto(
    val minClock: String,
    val maxClock: String,
    val currentClock: String
)
