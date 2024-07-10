package com.minux.monitoring.feature.devices.model.gpu.summary

class GPUIndicators(
    val memoryTemperature: Int,
    val coreTemperature: Int,
    val fanSpeed: Int,
    val power: Int,
    val powerUnit: String
)