package com.minux.monitoring.feature.presets.impl.presentation.mapper

import com.minux.monitoring.feature.presets.impl.data.model.PresetGroupDto
import com.minux.monitoring.feature.presets.impl.presentation.model.DevicePresetGroupItemModel

internal fun PresetGroupDto.toDevicePresetGroupItemModel(): DevicePresetGroupItemModel {
    return DevicePresetGroupItemModel(
        name = name,
        presets = presets.mapNotNull { it?.toPresetItemModel() }
    )
}