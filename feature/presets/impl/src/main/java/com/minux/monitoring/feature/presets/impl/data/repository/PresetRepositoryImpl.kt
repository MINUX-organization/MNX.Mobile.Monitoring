package com.minux.monitoring.feature.presets.impl.data.repository

import com.minux.monitoring.feature.presets.impl.data.datasource.PresetApiService
import com.minux.monitoring.feature.presets.impl.data.model.PresetAppliedDevicesGetDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetChangeDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetDevicesApplyDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetGetAllDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetGetDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetGroupDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetInputDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetRemoveDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetSupportedDevicesGetDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceGroupDto
import kotlinx.coroutines.flow.Flow

internal class PresetRepositoryImpl(private val presetApiService: PresetApiService) : PresetRepository {

    override fun getAllPresetsGroupedByGpus(): Flow<Result<List<PresetGroupDto>>> {
        return presetApiService.getAllPresetsGroupedByGpus()
    }

    override fun getAllPresets(presetGetAll: PresetGetAllDto): Flow<Result<List<PresetDto>>> {
        return presetApiService.getAllPresets(gpuName = presetGetAll.gpuName)
    }

    override fun getPreset(presetGet: PresetGetDto): Flow<Result<PresetDto>> {
        return presetApiService.getPreset(id = presetGet.presetId)
    }

    override fun getPresetSupportedDevices(
        presetSupportedDevicesGet: PresetSupportedDevicesGetDto
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>> {
        return presetApiService.getPresetSupportedDevices(id = presetSupportedDevicesGet.presetId)
    }

    override fun getPresetAppliedDevices(
        presetAppliedDevicesGet: PresetAppliedDevicesGetDto
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>> {
        return presetApiService.getPresetAppliedDevices(id = presetAppliedDevicesGet.presetId)
    }

    override fun applyDevicesForPreset(
        presetDevicesApply: PresetDevicesApplyDto
    ): Flow<Result<List<String>>> {
        return presetApiService.applyDevicesForPreset(
            id = presetDevicesApply.presetId,
            devices = presetDevicesApply.devices
        )
    }

    override fun addPreset(presetInput: PresetInputDto): Flow<Result<PresetDto>> {
        return presetApiService.addPreset(input = presetInput)
    }

    override fun changePreset(presetChange: PresetChangeDto): Flow<Result<PresetDto>> {
        return presetApiService.changePreset(
            id = presetChange.id,
            changingInput = presetChange.input
        )
    }

    override fun removePreset(presetRemove: PresetRemoveDto): Flow<Result<Unit>> {
        return presetApiService.removePreset(id = presetRemove.id)
    }
}