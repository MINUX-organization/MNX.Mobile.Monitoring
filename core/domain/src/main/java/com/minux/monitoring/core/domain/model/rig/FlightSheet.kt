package com.minux.monitoring.core.domain.model.rig

import com.minux.monitoring.core.domain.model.metrics.Shares
import com.minux.monitoring.core.domain.model.metrics.ValueUnit

data class FlightSheet(
    val name: String,
    val coin: String,
    val miner: String,
    val hashRate: ValueUnit? = null,
    val shares: Shares? = null
)
