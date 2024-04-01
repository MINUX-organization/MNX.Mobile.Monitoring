package com.minux.monitoring.core.domain.model.rig

import com.minux.monitoring.core.domain.model.metrics.ValueUnit

data class RigState(
    val id: String,
    val gpusState: List<String>,
    val isActive: Boolean,
    val onlineState: String,
    val averageTemperature: Int,
    val fanSpeed: Int,
    val power: ValueUnit,
    val miningUpTime: String,
    val bootedUpTime: String,
    val flightSheetInfo: List<FlightSheet>
)
