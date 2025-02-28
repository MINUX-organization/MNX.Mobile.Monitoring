package com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary

internal class GpuIndicatorsModel(
    val memoryTemperature: Int,
    val coreTemperature: Int,
    val fanSpeed: Int,
    val power: Int,
    val powerUnit: String
)