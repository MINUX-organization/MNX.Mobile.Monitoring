package com.minux.monitoring.feature.devices.presentation.model.gpu.summary

import com.minux.monitoring.feature.devices.presentation.model.DeviceNameModel

class GPUSummaryModel(
    val identification: GPUIdentificationModel,
    val name: DeviceNameModel,
    val indicators: GPUIndicatorsModel
)