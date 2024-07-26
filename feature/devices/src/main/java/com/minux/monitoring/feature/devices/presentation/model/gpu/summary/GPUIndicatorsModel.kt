package com.minux.monitoring.feature.devices.presentation.model.gpu.summary

class GPUIndicatorsModel(
    val memoryTemperature: Int,
    val coreTemperature: Int,
    val fanSpeed: Int,
    val power: Int,
    val powerUnit: String
)