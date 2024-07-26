package com.minux.monitoring.feature.devices.presentation.model.cpu

import com.minux.monitoring.feature.devices.presentation.model.DeviceNameModel

class CPUSummaryModel(
    val index: Int,
    val name: DeviceNameModel,
    val indicators: CPUIndicatorsModel
)