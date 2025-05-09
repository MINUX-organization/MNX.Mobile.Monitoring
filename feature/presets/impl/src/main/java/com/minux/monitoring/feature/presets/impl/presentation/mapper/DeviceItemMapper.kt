package com.minux.monitoring.feature.presets.impl.presentation.mapper

import com.minux.monitoring.feature.presets.impl.data.model.device.DeviceDto
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceItemModel

internal fun DeviceDto.toDeviceItemModel(checked: Boolean): DeviceItemModel {
    return DeviceItemModel(
        id = id,
        pciBus = pciBus,
        type = type,
        model = model,
        manufacturer = manufacturer,
        minerName = minerName,
        isOnline = isOnline,
        rigName = rigName,
        presetName = presetName,
        isChecked = checked
    )
}