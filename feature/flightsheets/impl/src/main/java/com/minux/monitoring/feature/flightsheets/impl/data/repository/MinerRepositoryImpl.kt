package com.minux.monitoring.feature.flightsheets.impl.data.repository

import com.minux.monitoring.feature.flightsheets.impl.data.datasource.MinerApiService
import com.minux.monitoring.feature.flightsheets.impl.data.model.device.miner.DeviceMinerDto
import kotlinx.coroutines.flow.Flow

internal class MinerRepositoryImpl(private val minerApiService: MinerApiService) : MinerRepository {

    override fun getAvailableMiners(): Flow<Result<List<DeviceMinerDto>>> {
        return minerApiService.getAvailableMiners()
    }
}