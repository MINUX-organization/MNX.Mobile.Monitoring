package com.minux.monitoring.core.data.model.rig

data class RigInformation(
    val id: String,
    val index: Int,
    val name: String,
    val localIp: String,
    val minuxVersion: String,
    val nvidiaCount: Int,
    val amdCount: Int,
    val intelCount: Int,
    val flightSheetInfo: List<FlightSheet>
)
