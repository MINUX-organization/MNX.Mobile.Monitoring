package com.minux.monitoring.core.network.model.monitoring.common

open class CoinStatisticsDto(
    val coin: String = "",
    val hashRate: ValueUnitDto = ValueUnitDto(0, ""),
    val shares: SharesDto = SharesDto(0, 0)
)