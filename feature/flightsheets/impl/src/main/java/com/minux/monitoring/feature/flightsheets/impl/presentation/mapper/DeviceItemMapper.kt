package com.minux.monitoring.feature.flightsheets.impl.presentation.mapper

import com.minux.monitoring.feature.flightsheets.impl.data.model.device.DeviceDto
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.DeviceItemModel

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
        flightSheetName = flightSheetName,
        isChecked = checked
    )
}