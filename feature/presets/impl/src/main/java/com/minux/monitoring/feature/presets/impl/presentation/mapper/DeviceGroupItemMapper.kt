package com.minux.monitoring.feature.presets.impl.presentation.mapper

import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceDto
import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceGroupDto
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceGroupItemModel
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceItemModel

internal fun DeviceGroupDto<DeviceGroupDto<DeviceDto>>.toDeviceGroupItemModel(
    devicesApplied: Set<String>
): DeviceGroupItemModel<DeviceGroupItemModel<DeviceItemModel>> {
    return DeviceGroupItemModel(
        name = name,
        elements = elements.mapNotNull { deviceGroup ->
            deviceGroup?.run {
                DeviceGroupItemModel(
                    name = deviceGroup.name,
                    elements = deviceGroup.elements.mapNotNull { device ->
                        device?.run {
                            device.toDeviceItemModel(checked = devicesApplied.contains(device.id))
                        }
                    }
                )
            }
        }
    )
}