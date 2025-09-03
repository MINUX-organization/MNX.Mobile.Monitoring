package com.minux.monitoring.feature.flightsheets.impl.presentation.model

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetModel

internal data class FlightSheetItemModel(
    val id: String = "",
    val name: String = "",
    val targets: List<FlightSheetTargetModel>
)