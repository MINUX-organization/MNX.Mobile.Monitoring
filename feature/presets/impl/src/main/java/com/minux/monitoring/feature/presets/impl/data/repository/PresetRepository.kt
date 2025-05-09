package com.minux.monitoring.feature.presets.impl.data.repository

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
import retrofit2.http.Path

internal interface PresetRepository {
    fun getAllPresetsGroupedByGpus(): Flow<Result<List<PresetGroupDto>>>

    fun getAllPresets(presetGetAll: PresetGetAllDto): Flow<Result<List<PresetDto>>>

    fun getPreset(presetGet: PresetGetDto): Flow<Result<PresetDto>>

    fun getPresetSupportedDevices(
        presetSupportedDevicesGet: PresetSupportedDevicesGetDto
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>>

    fun getPresetAppliedDevices(
        presetAppliedDevicesGet: PresetAppliedDevicesGetDto
    ): Flow<Result<List<DeviceGroupDto<DeviceGroupDto<DeviceDto>>>>>

    fun applyDevicesForPreset(presetDevicesApply: PresetDevicesApplyDto): Flow<Result<List<String>>>

    fun addPreset(presetInput: PresetInputDto): Flow<Result<PresetDto>>

    fun changePreset(presetChange: PresetChangeDto): Flow<Result<PresetDto>>

    fun removePreset(presetRemove: PresetRemoveDto): Flow<Result<Unit>>
}