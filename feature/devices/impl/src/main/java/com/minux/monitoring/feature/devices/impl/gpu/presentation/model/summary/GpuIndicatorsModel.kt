package com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary

internal class GpuIndicatorsModel(
    val memoryTemperature: Int = 0,
    val coreTemperature: Int = 0,
    val fanSpeed: Int = 0,
    val power: Int = 0
)