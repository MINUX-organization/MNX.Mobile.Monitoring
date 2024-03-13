package com.minux.monitoring.core.network.model.management.preset

data class PresetInputDto(
    val memoryClock: Int,
    val coreClock: Int,
    val powerLimit: Int,
    val criticalTemperature: Int,
    val fanSpeed: Int
)
