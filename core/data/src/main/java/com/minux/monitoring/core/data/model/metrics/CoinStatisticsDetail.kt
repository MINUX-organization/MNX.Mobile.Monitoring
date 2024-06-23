package com.minux.monitoring.core.data.model.metrics

data class CoinStatisticsDetail(
    val coin: String,
    val algorithm: String,
    val hashRate: ValueUnit,
    val shares: Shares
)
