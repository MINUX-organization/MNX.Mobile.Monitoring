package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.monitoring.common.FlightSheetDto
import com.minux.monitoring.core.network.model.monitoring.common.ValueUnitDto

data class RigStateDto(
    val id: String,
    val gpusState: List<String>,
    val isActive: Boolean,
    val onlineState: String,
    val averageTemperature: Int,
    val fanSpeed: Int,
    val power: ValueUnitDto,
    val miningUpTime: String,
    val bootedUpTime: String,
    val flightSheetInfo: List<FlightSheetDto>
)
