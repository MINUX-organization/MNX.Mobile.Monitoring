package com.minux.monitoring.feature.flightsheets.impl.data.datasource

import com.minux.monitoring.feature.flightsheets.impl.data.model.device.miner.DeviceMinerDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

internal interface MinerApiService {

    @GET("miners/available")
    fun getAvailableMiners(): Flow<Result<List<DeviceMinerDto>>>
}