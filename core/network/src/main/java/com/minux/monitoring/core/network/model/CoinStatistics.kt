package com.minux.monitoring.core.network.model

data class CoinStatistics(
    val coin: String,
    val algorithm: String,
    val hashRate: ValueUnit,
    val shares: Shares
)