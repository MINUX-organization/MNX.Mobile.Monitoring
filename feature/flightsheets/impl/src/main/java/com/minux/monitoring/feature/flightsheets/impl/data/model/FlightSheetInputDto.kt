package com.minux.monitoring.feature.flightsheets.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class FlightSheetInputDto(
    val name: String?,
    val targets: List<FlightSheetTargetInputDto?>
)