package com.minux.monitoring.feature.devices.impl.gpu.data.model

import com.minux.monitoring.feature.devices.impl.common.data.model.mining.FlightSheetDto
import com.minux.monitoring.feature.devices.impl.common.data.model.mining.MiningStateDto

internal class GpuIndicatorsDto(
    val deviceId: String,
    val deviceName: String,
    val power: Int,
    val fanSpeed: Int,
    val coreTemperature: Int,
    val memoryTemperature: Int,
    val flightSheet: FlightSheetDto,
    val miningState: MiningStateDto
)