package com.minux.monitoring.core.network.signalr

import com.minux.monitoring.core.network.MNXAsyncApiClient
import com.minux.monitoring.core.network.MNXAsyncApiService
import com.minux.monitoring.core.network.model.monitoring.device.DeviceDynamicDataDto
import com.minux.monitoring.core.network.model.monitoring.device.DeviceInformationDto
import com.minux.monitoring.core.network.model.monitoring.HashRateDto
import com.minux.monitoring.core.network.model.monitoring.RigCommandDto
import com.minux.monitoring.core.network.model.monitoring.SendCoinDto
import com.minux.monitoring.core.network.model.monitoring.TotalDataChangeDto
import com.minux.monitoring.core.network.model.monitoring.RigStateChangeDto
import com.minux.monitoring.core.network.model.monitoring.RigDynamicDataDto
import com.minux.monitoring.core.network.model.monitoring.RigInformationDto
import com.minux.monitoring.core.network.model.monitoring.RigStateDto
import com.minux.monitoring.core.network.model.monitoring.rig.DetailRigInformationDto
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

    override fun sendRigPowerOffCommand(rigCommandDto: RigCommandDto): Flow<Result<Unit>> {
        return monitoringConnection.onSend(
            method = "SendRigPowerOffCommand",
            data = rigCommandDto.rigId
        )
    }

    override fun sendStartMiningCommand(rigCommandDto: RigCommandDto): Flow<Result<Unit>> {
        return monitoringConnection.onSend(
            method = "SendStartMiningCommand",
            data = rigCommandDto.rigId
        )
    }

    override fun sendStopMiningCommand(rigCommandDto: RigCommandDto): Flow<Result<Unit>> {
        return monitoringConnection.onSend(
            method = "SendStopMiningCommand",
            data = rigCommandDto.rigId
        )
    }

    override fun sendRebootRigCommand(rigCommandDto: RigCommandDto): Flow<Result<Unit>> {
        return monitoringConnection.onSend(
            method = "SendRebootRigCommand",
            data = rigCommandDto.rigId
        )
    }

    override fun receiveCurrentHashRate(): Flow<Result<HashRateDto>> {
        return monitoringConnection.onReceive(method = "ReceivedCurrentHashRate")
    }

    override fun receiveHashRateForPeriod(): Flow<Result<Array<HashRateDto>>> {
        return monitoringConnection.onReceive(method = "ReceivedHashRateForAPeriod")
    }

    override fun receiveRigsInformation(): Flow<Result<Array<RigInformationDto>>> {
        return monitoringConnection.onReceive(method = "ReceivedRigsInformation")
    }

    override fun receiveRigsState(): Flow<Result<Array<RigStateDto>>> {
        return monitoringConnection.onReceive(method = "ReceivedRigsState")
    }

    override fun receiveRigsDynamicData(): Flow<Result<Array<RigDynamicDataDto>>> {
        return monitoringConnection.onReceive(method = "ReceivedRigsDynamicData")
    }

    override fun receiveRigStateChange(): Flow<Result<RigStateChangeDto>> {
        return monitoringConnection.onReceive(method = "ReceivedRigStateChangeMessage")
    }

    override fun receiveTotalData(): Flow<Result<TotalDataChangeDto>> {
        return monitoringConnection.onReceive(method = "ReceivedRigStateChangeMessage")
    }

    override fun receiveDevicesInformation(): Flow<Result<Array<DeviceInformationDto>>> {
        return devicesConnection.onReceive(method = "ReceivedTotalData")
    }

    override fun receiveDevicesDynamicData(): Flow<Result<Array<DeviceDynamicDataDto>>> {
        return devicesConnection.onReceive(method = "ReceivedDevicesDynamicData")
    }

    override fun receiveDetailRigsInformation(): Flow<Result<Array<DetailRigInformationDto>>> {
        return rigsConnection.onReceive(method = "ReceivedDetailRigsInformation")
    }
}