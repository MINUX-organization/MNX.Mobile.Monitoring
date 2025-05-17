package com.minux.monitoring.feature.devices.impl.cpu.data.model

import com.minux.monitoring.feature.devices.impl.common.data.model.mining.FlightSheetDto
import com.minux.monitoring.feature.devices.impl.common.data.model.mining.MiningStateDto

internal class CpuIndicatorsDto(
    val deviceId: String,
    val deviceName: String,
    val power: Int,
    val fanSpeed: Int,
    val temperature: Int,
    val flightSheet: FlightSheetDto,
    val miningState: MiningStateDto
)