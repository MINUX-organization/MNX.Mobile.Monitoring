package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.FlightSheetInfo
import com.minux.monitoring.core.network.model.ValueUnit

data class RigsStateResponse(
    val id: String,
    val gpusState: List<String>,
    val isActive: Boolean,
    val onlineState: String,
    val averageTemperature: Int,
    val fanSpeed: Int,
    val power: ValueUnit,
    val miningUpTime: String,
    val bootedUpTime: String,
    val flightSheetInfo: List<FlightSheetInfo>
)
