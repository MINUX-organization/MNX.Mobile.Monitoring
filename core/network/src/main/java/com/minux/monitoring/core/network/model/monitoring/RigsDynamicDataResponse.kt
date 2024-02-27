package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.FlightSheetInfo
import com.minux.monitoring.core.network.model.ValueUnit

data class RigsDynamicDataResponse(
    val id: String,
    val index: Int,
    val averageTemperature: Int,
    val fanSpeed: Int,
    val power: ValueUnit,
    val internetSpeed: ValueUnit,
    val miningUpTime: String,
    val bootedUpTime: String,
    val flightSheetInfo: List<FlightSheetInfo>
)