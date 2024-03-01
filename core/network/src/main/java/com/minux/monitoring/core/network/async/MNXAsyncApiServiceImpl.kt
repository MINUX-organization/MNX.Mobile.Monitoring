package com.minux.monitoring.core.network.async

import com.minux.monitoring.core.network.MNXAsyncApiClient
import com.minux.monitoring.core.network.MNXAsyncApiService
import com.minux.monitoring.core.network.model.devices.DevicesDynamicDataDto
import com.minux.monitoring.core.network.model.devices.DevicesInformationDto
import com.minux.monitoring.core.network.model.monitoring.HashRateDto
import com.minux.monitoring.core.network.model.monitoring.SendCoinDto
import com.minux.monitoring.core.network.model.monitoring.TotalDataChangeDto
import com.minux.monitoring.core.network.model.monitoring.RigStateChangeDto
import com.minux.monitoring.core.network.model.monitoring.RigsDynamicDataDto
import com.minux.monitoring.core.network.model.monitoring.RigsInformationDto
import com.minux.monitoring.core.network.model.monitoring.RigsStateDto
import com.minux.monitoring.core.network.model.rigs.DetailRigsInformationDto
import kotlinx.coroutines.flow.Flow

internal class MNXAsyncApiServiceImpl : MNXAsyncApiService {
    private val monitoringConnection = MNXAsyncApiClient.getApiClient(endpoint = "monitoring")
    private val devicesConnection = MNXAsyncApiClient.getApiClient(endpoint = "devices")
    private val rigsConnection = MNXAsyncApiClient.getApiClient(endpoint = "rigs")

    override fun sendCoin(sendCoinDto: SendCoinDto): Flow<Result<Unit>> {
        return monitoringConnection.onSend(
            method = "SendCoin",
            data = sendCoinDto.chosenCoin
        )
    }

    override fun receiveCurrentHashRate(): Flow<Result<HashRateDto>> {
        return monitoringConnection.onReceive(method = "ReceivedCurrentHashRate")
    }

    override fun receiveHashRateForPeriod(): Flow<Result<Array<HashRateDto>>> {
        return monitoringConnection.onReceive(method = "ReceivedHashRateForAPeriod")
    }

    override fun receiveRigsInformation(): Flow<Result<Array<RigsInformationDto>>> {
        return monitoringConnection.onReceive(method = "ReceivedRigsInformation")
    }

    override fun receiveRigsState(): Flow<Result<Array<RigsStateDto>>> {
        return monitoringConnection.onReceive(method = "ReceivedRigsState")
    }

    override fun receiveRigsDynamicData(): Flow<Result<Array<RigsDynamicDataDto>>> {
        return monitoringConnection.onReceive(method = "ReceivedRigsDynamicData")
    }

    override fun receiveRigStateChange(): Flow<Result<RigStateChangeDto>> {
        return monitoringConnection.onReceive(method = "ReceivedRigStateChangeMessage")
    }

    override fun receiveTotalData(): Flow<Result<TotalDataChangeDto>> {
        return monitoringConnection.onReceive(method = "ReceivedRigStateChangeMessage")
    }

    override fun receiveDevicesInformation(): Flow<Result<DevicesInformationDto>> {
        return devicesConnection.onReceive(method = "ReceivedTotalData")
    }

    override fun receiveDevicesDynamicData(): Flow<Result<DevicesDynamicDataDto>> {
        return devicesConnection.onReceive(method = "ReceivedDevicesDynamicData")
    }

    override fun receiveDetailRigsInformation(): Flow<Result<DetailRigsInformationDto>> {
        return rigsConnection.onReceive(method = "ReceivedDetailRigsInformation")
    }
}