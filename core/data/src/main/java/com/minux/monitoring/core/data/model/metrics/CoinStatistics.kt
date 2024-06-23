package com.minux.monitoring.core.data.model.metrics

data class CoinStatistics(
    val coin: String,
    val hashRate: ValueUnit,
    val shares: Shares
)
