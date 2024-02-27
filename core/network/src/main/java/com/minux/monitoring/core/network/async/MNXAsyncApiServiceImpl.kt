package com.minux.monitoring.core.network.async

import com.minux.monitoring.core.network.MNXAsyncApiClient
import com.minux.monitoring.core.network.MNXAsyncApiService
import com.minux.monitoring.core.network.model.monitoring.HashRateResponse
import com.minux.monitoring.core.network.model.monitoring.SendCoinRequest
import com.minux.monitoring.core.network.model.monitoring.TotalDataChangeResponse
import com.minux.monitoring.core.network.model.monitoring.RigStateChangeResponse
import com.minux.monitoring.core.network.model.monitoring.RigsDynamicDataResponse
import com.minux.monitoring.core.network.model.monitoring.RigsInformationResponse
import com.minux.monitoring.core.network.model.monitoring.RigsStateResponse
import kotlinx.coroutines.flow.Flow

internal class MNXAsyncApiServiceImpl : MNXAsyncApiService {
    private val monitoringConnection = MNXAsyncApiClient.getApiClient("monitoring")

    override fun sendCoin(sendCoinRequest: SendCoinRequest): Flow<Result<Unit>> {
        return monitoringConnection.onSend(
            method = "SendCoin",
            data = sendCoinRequest.chosenCoin
        )
    }

    override fun receiveCurrentHashRate(): Flow<Result<HashRateResponse>> {
        return monitoringConnection.onReceive(method = "ReceivedCurrentHashRate")
    }

    override fun receiveHashRateForPeriod(): Flow<Result<Array<HashRateResponse>>> {
        return monitoringConnection.onReceive(method = "ReceivedHashRateForAPeriod")
    }

    override fun receiveRigsInformation(): Flow<Result<Array<RigsInformationResponse>>> {
        return monitoringConnection.onReceive(method = "ReceivedWorkersInformation")
    }

    override fun receiveRigsState(): Flow<Result<Array<RigsStateResponse>>> {
        return monitoringConnection.onReceive(method = "ReceivedWorkersState")
    }

    override fun receiveRigsDynamicData(): Flow<Result<Array<RigsDynamicDataResponse>>> {
        return monitoringConnection.onReceive(method = "ReceivedWorkersDynamicData")
    }

    override fun receiveRigStateChange(): Flow<Result<RigStateChangeResponse>> {
        return monitoringConnection.onReceive(method = "ReceivedWorkerStateChangeMessage")
    }

    override fun receiveTotalData(): Flow<Result<TotalDataChangeResponse>> {
        return monitoringConnection.onReceive(method = "ReceivedWorkerStateChangeMessage")
    }
}