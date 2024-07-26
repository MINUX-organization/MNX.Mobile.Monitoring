package com.minux.monitoring.feature.devices.data.model

import com.minux.monitoring.core.data.model.metrics.CoinStatistics
import com.minux.monitoring.core.data.model.metrics.ValueUnit

data class DeviceDynamicData(
    val id: String,
    val index: Int,
    val isActive: Boolean,
    val temperature: Int,
    val fanSpeed: Int,
    val power: ValueUnit,
    val coins: List<CoinStatistics>
)
