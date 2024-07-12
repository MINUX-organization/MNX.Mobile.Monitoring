package com.minux.monitoring.core.data.repository

import com.minux.monitoring.core.data.model.device.DeviceDynamicData
import com.minux.monitoring.core.data.model.device.DeviceInformation
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getDevicesDynamicData(): Flow<Result<List<DeviceDynamicData>>>

    fun getDevicesInformation(): Flow<Result<List<DeviceInformation>>>
}