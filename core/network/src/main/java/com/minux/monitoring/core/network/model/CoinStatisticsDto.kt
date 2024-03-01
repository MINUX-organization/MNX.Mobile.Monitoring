package com.minux.monitoring.core.network.model

open class CoinStatisticsDto(
    val coin: String = "",
    val hashRate: ValueUnitDto = ValueUnitDto(0, ""),
    val shares: SharesDto = SharesDto(0, 0)
)