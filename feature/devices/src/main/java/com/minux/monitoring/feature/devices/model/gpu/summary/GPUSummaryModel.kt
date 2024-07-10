package com.minux.monitoring.feature.devices.model.gpu.summary

import com.minux.monitoring.feature.devices.model.DeviceNameModel

class GPUSummaryModel(
    val identification: GPUIdentificationModel,
    val name: DeviceNameModel,
    val indicators: GPUIndicators
)