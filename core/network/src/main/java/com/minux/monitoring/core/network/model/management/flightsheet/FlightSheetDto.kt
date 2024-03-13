package com.minux.monitoring.core.network.model.management.flightsheet

import com.minux.monitoring.core.network.model.management.common.MinerDto
import com.minux.monitoring.core.network.model.management.pool.PoolDto

data class FlightSheetDto(
    val id: String,
    val name: String,
    val miner: MinerDto,
    val cryptocurrency: String,
    val walletAddress: String,
    val pool: PoolDto
)
