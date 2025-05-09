package com.minux.monitoring.feature.presets.impl.data.repository

import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingGetDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingSetDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceRestrictionsDto
import com.minux.monitoring.feature.presets.impl.data.model.device.GpuRestrictionsGetByIdDto
import com.minux.monitoring.feature.presets.impl.data.model.device.GpuRestrictionsGetByNameDto
import kotlinx.coroutines.flow.Flow

internal interface DeviceRepository {
    fun getGpuNames(): Flow<Result<List<String>>>

    fun getGpuRestrictionsById(gpuRestrictionsGetById: GpuRestrictionsGetByIdDto): Flow<Result<DeviceRestrictionsDto.GpuRestrictionsDto>>

    fun getGpuRestrictionsByName(gpuRestrictionsGetByName: GpuRestrictionsGetByNameDto): Flow<Result<DeviceRestrictionsDto.GpuRestrictionsDto>>

    fun getDeviceOverclocking(deviceOverclockingGet: DeviceOverclockingGetDto): Flow<Result<DeviceOverclockingDto>>

    fun setDeviceOverclocking(deviceOverclockingSet: DeviceOverclockingSetDto): Flow<Result<List<String>>>
}