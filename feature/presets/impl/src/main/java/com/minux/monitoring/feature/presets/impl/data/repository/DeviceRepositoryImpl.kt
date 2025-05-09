package com.minux.monitoring.feature.presets.impl.data.repository

import com.minux.monitoring.feature.presets.impl.data.datasource.DeviceApiService
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingGetDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingSetDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceRestrictionsDto
import com.minux.monitoring.feature.presets.impl.data.model.device.GpuRestrictionsGetByIdDto
import com.minux.monitoring.feature.presets.impl.data.model.device.GpuRestrictionsGetByNameDto
import kotlinx.coroutines.flow.Flow

internal class DeviceRepositoryImpl(private val deviceApiService: DeviceApiService) : DeviceRepository {
    override fun getGpuNames(): Flow<Result<List<String>>> {
        return deviceApiService.getGpuNames()
    }

    override fun getGpuRestrictionsById(gpuRestrictionsGetById: GpuRestrictionsGetByIdDto): Flow<Result<DeviceRestrictionsDto.GpuRestrictionsDto>> {
        return deviceApiService.getGpuRestrictionsById(id = gpuRestrictionsGetById.gpuId)
    }

    override fun getGpuRestrictionsByName(gpuRestrictionsGetByName: GpuRestrictionsGetByNameDto): Flow<Result<DeviceRestrictionsDto.GpuRestrictionsDto>> {
        return deviceApiService.getGpuRestrictionsByName(name = gpuRestrictionsGetByName.gpuName)
    }

    override fun getDeviceOverclocking(deviceOverclockingGet: DeviceOverclockingGetDto): Flow<Result<DeviceOverclockingDto>> {
        return deviceApiService.getDeviceOverclocking(id = deviceOverclockingGet.id)
    }

    override fun setDeviceOverclocking(deviceOverclockingSet: DeviceOverclockingSetDto): Flow<Result<List<String>>> {
        return deviceApiService.setDeviceOverclocking(id = deviceOverclockingSet.id)
    }
}