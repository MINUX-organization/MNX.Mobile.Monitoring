package com.minux.monitoring.core.network

import com.minux.monitoring.core.network.model.monitoring.HashRateResponse
import com.minux.monitoring.core.network.model.monitoring.SendCoinRequest
import com.minux.monitoring.core.network.model.monitoring.TotalDataChangeResponse
import com.minux.monitoring.core.network.model.monitoring.RigStateChangeResponse
import com.minux.monitoring.core.network.model.monitoring.RigsDynamicDataResponse
import com.minux.monitoring.core.network.model.monitoring.RigsInformationResponse
import com.minux.monitoring.core.network.model.monitoring.RigsStateResponse
import kotlinx.coroutines.flow.Flow

interface MNXAsyncApiService {
    fun sendCoin(sendCoinRequest: SendCoinRequest): Flow<Result<Unit>>

    fun receiveCurrentHashRate(): Flow<Result<HashRateResponse>>

    fun receiveHashRateForPeriod(): Flow<Result<Array<HashRateResponse>>>

    fun receiveRigsInformation(): Flow<Result<Array<RigsInformationResponse>>>

    fun receiveRigsState(): Flow<Result<Array<RigsStateResponse>>>

    fun receiveRigsDynamicData(): Flow<Result<Array<RigsDynamicDataResponse>>>

    fun receiveRigStateChange(): Flow<Result<RigStateChangeResponse>>

    fun receiveTotalData(): Flow<Result<TotalDataChangeResponse>>
}