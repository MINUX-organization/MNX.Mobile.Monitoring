package com.minux.monitoring.core.network.model

data class FlightSheetDto(
    val name: String,
    val coin: String,
    val miner: String,
    val hashRate: ValueUnitDto? = null,
    val shares: SharesDto? = null
)