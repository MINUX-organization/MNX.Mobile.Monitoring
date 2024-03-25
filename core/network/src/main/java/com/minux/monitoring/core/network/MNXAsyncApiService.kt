package com.minux.monitoring.core.network

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

interface MNXAsyncApiService {
    fun sendCoin(sendCoinDto: SendCoinDto): Flow<Result<Unit>>

    fun sendRigPowerOffCommand(rigCommandDto: RigCommandDto): Flow<Result<Unit>>

    fun sendStartMiningCommand(rigCommandDto: RigCommandDto): Flow<Result<Unit>>

    fun sendStopMiningCommand(rigCommandDto: RigCommandDto): Flow<Result<Unit>>

    fun sendRebootRigCommand(rigCommandDto: RigCommandDto): Flow<Result<Unit>>

    fun receiveCurrentHashRate(): Flow<Result<HashRateDto>>

    fun receiveHashRateForPeriod(): Flow<Result<Array<HashRateDto>>>

    fun receiveRigsInformation(): Flow<Result<Array<RigInformationDto>>>

    fun receiveRigsState(): Flow<Result<Array<RigStateDto>>>

    fun receiveRigsDynamicData(): Flow<Result<Array<RigDynamicDataDto>>>

    fun receiveRigStateChange(): Flow<Result<RigStateChangeDto>>

    fun receiveTotalData(): Flow<Result<TotalDataChangeDto>>

    fun receiveDevicesInformation(): Flow<Result<Array<DeviceInformationDto>>>

    fun receiveDevicesDynamicData(): Flow<Result<Array<DeviceDynamicDataDto>>>

    fun receiveDetailRigsInformation(): Flow<Result<Array<DetailRigInformationDto>>>
}