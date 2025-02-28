package com.minux.monitoring.feature.devices.impl.gpu.presentation.mapper

import com.minux.monitoring.feature.devices.impl.common.data.model.DevicePci
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuIdentificationModel

internal fun DevicePci?.toGpuIdentificationModel(): GpuIdentificationModel {
    return GpuIdentificationModel(
        index = this?.id,
        bus = this?.bus
    )
}