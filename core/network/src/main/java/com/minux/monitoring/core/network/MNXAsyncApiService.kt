package com.minux.monitoring.core.network

import com.minux.monitoring.core.network.model.monitoring.devices.DevicesDynamicDataDto
import com.minux.monitoring.core.network.model.monitoring.devices.DevicesInformationDto
import com.minux.monitoring.core.network.model.monitoring.HashRateDto
import com.minux.monitoring.core.network.model.monitoring.SendCoinDto
import com.minux.monitoring.core.network.model.monitoring.TotalDataChangeDto
import com.minux.monitoring.core.network.model.monitoring.RigStateChangeDto
import com.minux.monitoring.core.network.model.monitoring.RigsDynamicDataDto
import com.minux.monitoring.core.network.model.monitoring.RigsInformationDto
import com.minux.monitoring.core.network.model.monitoring.RigsStateDto
import com.minux.monitoring.core.network.model.monitoring.rigs.DetailRigsInformationDto
import kotlinx.coroutines.flow.Flow

interface MNXAsyncApiService {
    fun sendCoin(sendCoinDto: SendCoinDto): Flow<Result<Unit>>

    fun receiveCurrentHashRate(): Flow<Result<HashRateDto>>

    fun receiveHashRateForPeriod(): Flow<Result<Array<HashRateDto>>>

    fun receiveRigsInformation(): Flow<Result<Array<RigsInformationDto>>>

    fun receiveRigsState(): Flow<Result<Array<RigsStateDto>>>

    fun receiveRigsDynamicData(): Flow<Result<Array<RigsDynamicDataDto>>>

    fun receiveRigStateChange(): Flow<Result<RigStateChangeDto>>

    fun receiveTotalData(): Flow<Result<TotalDataChangeDto>>

    fun receiveDevicesInformation(): Flow<Result<DevicesInformationDto>>

    fun receiveDevicesDynamicData(): Flow<Result<DevicesDynamicDataDto>>

    fun receiveDetailRigsInformation(): Flow<Result<DetailRigsInformationDto>>
}