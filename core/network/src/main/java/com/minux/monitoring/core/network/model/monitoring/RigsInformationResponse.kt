package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.FlightSheetInfo

data class RigsInformationResponse(
    val id: String,
    val index: Int,
    val name: String,
    val localIp: String,
    val minuxVersion: String,
    val nvidiaCount: Int,
    val amdCount: Int,
    val intelCount: Int,
    val flightSheetInfo: List<FlightSheetInfo>
)