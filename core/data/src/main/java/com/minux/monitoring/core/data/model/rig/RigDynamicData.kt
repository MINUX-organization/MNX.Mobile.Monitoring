package com.minux.monitoring.core.data.model.rig

import com.minux.monitoring.core.data.model.metrics.ValueUnit

data class RigDynamicData(
    val id: String,
    val index: Int,
    val averageTemperature: Int,
    val fanSpeed: Int,
    val power: ValueUnit,
    val internetSpeed: ValueUnit,
    val miningUpTime: String,
    val bootedUpTime: String,
    val flightSheetInfo: List<FlightSheet>
)
