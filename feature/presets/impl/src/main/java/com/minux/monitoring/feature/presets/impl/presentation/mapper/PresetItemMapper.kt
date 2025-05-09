package com.minux.monitoring.feature.presets.impl.presentation.mapper

import com.minux.monitoring.feature.presets.impl.data.model.PresetChangeDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetChangeInputDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetDto
import com.minux.monitoring.feature.presets.impl.data.model.PresetInputDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceOverclockingDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceRestrictionsDto
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.PresetItemModel

internal fun PresetDto.toPresetItemModel(): PresetItemModel {
    val deviceParametersModel = when (overclocking) {
        is DeviceOverclockingDto.CpuOverclockingDto -> { DeviceParametersModel.CpuParametersModel }

        is DeviceOverclockingDto.GpuOverclockingDto -> overclocking.toDeviceParametersModel()
    }

    return PresetItemModel(
        id = id,
        parameters = deviceParametersModel
    )
}

internal fun PresetDto.toPresetItemModel(gpuRestrictions: DeviceRestrictionsDto.GpuRestrictionsDto): PresetItemModel {
    val deviceParametersModel = when (overclocking) {
        is DeviceOverclockingDto.CpuOverclockingDto -> { DeviceParametersModel.CpuParametersModel }

        is DeviceOverclockingDto.GpuOverclockingDto -> {
            overclocking.toDeviceParametersModel(restrictions = gpuRestrictions)
        }
    }

    return PresetItemModel(
        id = id,
        parametersIsLoading = false,
        parameters = deviceParametersModel
    )
}

internal fun PresetItemModel.toPresetInputDto(): PresetInputDto {
    return PresetInputDto(
        name = info.presetName,
        deviceName = info.deviceName,
        overclocking = this.parameters?.toDeviceOverclockingDto()
    )
}

internal fun PresetItemModel.toPresetChangeDto(): PresetChangeDto {
    return PresetChangeDto(
        id = id,
        input = PresetChangeInputDto(
            name = this.info.presetName,
            overclocking = this.parameters?.toDeviceOverclockingDto()
        )
    )
}