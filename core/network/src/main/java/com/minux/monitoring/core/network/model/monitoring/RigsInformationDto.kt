package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.FlightSheetDto

data class RigsInformationDto(
    val id: String,
    val index: Int,
    val name: String,
    val localIp: String,
    val minuxVersion: String,
    val nvidiaCount: Int,
    val amdCount: Int,
    val intelCount: Int,
    val flightSheetInfo: List<FlightSheetDto>
)