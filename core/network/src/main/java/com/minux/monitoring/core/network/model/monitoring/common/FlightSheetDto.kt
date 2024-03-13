package com.minux.monitoring.core.network.model.monitoring.common

data class FlightSheetDto(
    val name: String,
    val coin: String,
    val miner: String,
    val hashRate: ValueUnitDto? = null,
    val shares: SharesDto? = null
)