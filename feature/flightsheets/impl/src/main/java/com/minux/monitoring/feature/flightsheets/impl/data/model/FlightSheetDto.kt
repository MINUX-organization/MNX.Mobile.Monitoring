package com.minux.monitoring.feature.flightsheets.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class FlightSheetDto(
    val id: String,
    val name: String?,
    val targets: List<FlightSheetTargetDto?>
)