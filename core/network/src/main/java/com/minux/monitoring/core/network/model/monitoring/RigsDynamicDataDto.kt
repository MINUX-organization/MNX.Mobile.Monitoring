package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.FlightSheetDto
import com.minux.monitoring.core.network.model.ValueUnitDto

data class RigsDynamicDataDto(
    val id: String,
    val index: Int,
    val averageTemperature: Int,
    val fanSpeed: Int,
    val power: ValueUnitDto,
    val internetSpeed: ValueUnitDto,
    val miningUpTime: String,
    val bootedUpTime: String,
    val flightSheetInfo: List<FlightSheetDto>
)