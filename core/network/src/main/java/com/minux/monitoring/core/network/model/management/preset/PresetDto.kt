package com.minux.monitoring.core.network.model.management.preset

data class PresetDto(
    val id: String,
    val memoryClock: Int,
    val coreClock: Int,
    val powerLimit: Int,
    val criticalTemperature: Int,
    val fanSpeed: Int,
    val gpuName: String
)
