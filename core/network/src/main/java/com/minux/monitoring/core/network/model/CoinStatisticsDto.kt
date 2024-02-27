package com.minux.monitoring.core.network.model

data class CoinStatisticsDto(
    val coin: String,
    val algorithm: String,
    val hashRate: ValueUnitDto,
    val shares: SharesDto
)