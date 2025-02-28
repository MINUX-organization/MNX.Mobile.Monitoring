package com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary

import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceNameModel

internal class GpuSummaryModel(
    val identification: GpuIdentificationModel,
    val name: DeviceNameModel
)