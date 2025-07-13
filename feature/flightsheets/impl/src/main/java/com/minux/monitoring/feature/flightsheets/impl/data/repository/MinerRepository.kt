package com.minux.monitoring.feature.flightsheets.impl.data.repository

import com.minux.monitoring.feature.flightsheets.impl.data.model.device.miner.DeviceMinerDto
import kotlinx.coroutines.flow.Flow

internal interface MinerRepository {
    fun getAvailableMiners(): Flow<Result<List<DeviceMinerDto>>>
}