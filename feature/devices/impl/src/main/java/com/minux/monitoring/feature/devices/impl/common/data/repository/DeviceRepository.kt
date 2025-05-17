package com.minux.monitoring.feature.devices.impl.common.data.repository

import com.minux.monitoring.feature.devices.impl.common.data.model.DeviceIndicatorsDto
import kotlinx.coroutines.flow.Flow

internal interface DeviceRepository {
    fun observeDeviceIndicators(): Flow<Result<DeviceIndicatorsDto>>
}