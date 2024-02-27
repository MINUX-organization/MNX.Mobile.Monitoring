package com.minux.monitoring.core.network.model

data class FlightSheetInfo(
    val name: String,
    val coin: String,
    val miner: String,
    val hashRate: ValueUnit? = null,
    val shares: Shares? = null
)