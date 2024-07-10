package com.minux.monitoring.feature.devices.model.cpu

import com.minux.monitoring.feature.devices.model.DeviceNameModel

class CPUSummaryModel(
    val index: Int,
    val name: DeviceNameModel,
    val indicators: CPUIndicatorsModel
)