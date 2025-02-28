package com.minux.monitoring.feature.devices.impl.cpu.presentation.model

internal class CpuIndicatorsModel(
    val temperature: Int,
    val fanSpeed: Int,
    val power: Int,
    val powerUnit: String
)