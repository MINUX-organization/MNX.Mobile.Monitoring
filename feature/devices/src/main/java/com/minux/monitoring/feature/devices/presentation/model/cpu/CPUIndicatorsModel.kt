package com.minux.monitoring.feature.devices.presentation.model.cpu

class CPUIndicatorsModel(
    val temperature: Int,
    val fanSpeed: Int,
    val power: Int,
    val powerUnit: String
)