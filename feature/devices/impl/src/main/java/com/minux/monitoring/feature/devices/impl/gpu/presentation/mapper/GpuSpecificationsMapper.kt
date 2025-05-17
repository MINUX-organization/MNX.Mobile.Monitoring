package com.minux.monitoring.feature.devices.impl.gpu.presentation.mapper

import com.minux.monitoring.feature.devices.impl.gpu.data.model.info.GpuInfoDto
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuSpecificationsModel

internal fun GpuInfoDto?.toGpuSpecificationsModel(): GpuSpecificationsModel {
    return GpuSpecificationsModel(
        manufacturer = this?.manufacturer,
        vendor = this?.vendor,
        memorySize = this?.memory?.total,
        memoryVendor = this?.memory?.vendor,
        memoryType = this?.memory?.type
    )
}