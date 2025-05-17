package com.minux.monitoring.feature.devices.impl.common.data.repository

import com.microsoft.signalr.HubConnection
import com.minux.monitoring.core.network.api.hub.onReceiveStream
import com.minux.monitoring.feature.devices.impl.common.data.model.DeviceIndicatorsDto
import kotlinx.coroutines.flow.Flow

internal class DeviceRepositoryImpl(private val devicesConnection: HubConnection) : DeviceRepository {

    override fun observeDeviceIndicators(): Flow<Result<DeviceIndicatorsDto>> {
        return devicesConnection.onReceiveStream(subscriptionType = "Devices")
    }
}