package com.minux.monitoring.feature.devices.impl.cpu.presentation.model

internal class CpuSpecificationsModel(
    val manufacturer: String?,
    val coresCount: Int,
    val threadsCount: Int,
    val architecture: String?
)