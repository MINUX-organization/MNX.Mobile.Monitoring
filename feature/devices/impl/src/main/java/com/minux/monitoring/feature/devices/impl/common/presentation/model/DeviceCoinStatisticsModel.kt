package com.minux.monitoring.feature.devices.impl.common.presentation.model

internal data class DeviceCoinStatisticsModel(
    val coin: String?,
    val hashRate: Float,
    val hashRateUnit: String,
    val acceptedShare: Int,
    val rejectedShare: Int,
    val performance: Float?
)