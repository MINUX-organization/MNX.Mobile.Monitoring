package com.minux.monitoring.core.network.model.management.flightsheet

import com.minux.monitoring.core.network.model.management.common.MinerDto
import com.minux.monitoring.core.network.model.management.pool.PoolDto

data class FlightSheetInputDto(
    val name: String,
    val miner: MinerDto,
    val cryptocurrency: String,
    val walletAddress: String,
    val pool: PoolDto
)
