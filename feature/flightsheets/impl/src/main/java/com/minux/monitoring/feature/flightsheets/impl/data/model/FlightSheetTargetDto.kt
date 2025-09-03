package com.minux.monitoring.feature.flightsheets.impl.data.model

import com.minux.monitoring.feature.flightsheets.impl.data.model.device.DeviceMiningConfigDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.device.miner.DeviceMinerDto
import kotlinx.serialization.Serializable

@Serializable
internal class FlightSheetTargetDto(
    val miningConfig: DeviceMiningConfigDto?,
    val miner: DeviceMinerDto?
)