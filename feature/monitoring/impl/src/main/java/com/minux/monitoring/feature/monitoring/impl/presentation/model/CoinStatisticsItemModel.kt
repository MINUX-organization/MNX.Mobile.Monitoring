package com.minux.monitoring.feature.monitoring.impl.presentation.model

internal class CoinStatisticsItemModel(
    val coin: String,
    val algorithm: String,
    val hashRate: Int,
    val hashRateUnit: String,
    val accepted: Int,
    val rejected: Int
)