package com.minux.monitoring.feature.flightsheets.impl.data.model

import com.minux.monitoring.feature.flightsheets.impl.data.model.device.DeviceMiningConfigInputDto
import kotlinx.serialization.Serializable

@Serializable
internal class FlightSheetTargetInputDto(
    val miningConfig: DeviceMiningConfigInputDto?,
    val minerId: String
)