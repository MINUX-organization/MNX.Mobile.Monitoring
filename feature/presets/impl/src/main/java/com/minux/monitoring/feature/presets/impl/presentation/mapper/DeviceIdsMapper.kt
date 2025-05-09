package com.minux.monitoring.feature.presets.impl.presentation.mapper

import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceGroupDto

internal fun DeviceGroupDto<DeviceGroupDto<DeviceDto>>.toDeviceIds(): List<String> {
    return this.elements.flatMap { deviceGroup ->
        deviceGroup?.elements?.mapNotNull { device -> device?.id } ?: emptyList()
    }
}