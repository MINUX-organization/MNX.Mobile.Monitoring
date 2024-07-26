package com.minux.monitoring.feature.devices.data.repository

import com.minux.monitoring.feature.devices.data.model.DeviceDynamicData
import com.minux.monitoring.feature.devices.data.model.DeviceInformation
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getDevicesDynamicData(): Flow<Result<List<DeviceDynamicData>>>

    fun getDevicesInformation(): Flow<Result<List<DeviceInformation>>>
}