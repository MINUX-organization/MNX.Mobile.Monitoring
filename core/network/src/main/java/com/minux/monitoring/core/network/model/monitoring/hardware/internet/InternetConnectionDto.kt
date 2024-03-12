package com.minux.monitoring.core.network.model.monitoring.hardware.internet

data class InternetConnectionDto(
    val name: String,
    val model: String,
    val brand: String,
    val busInfo: String,
    val logicalName: String,
    val serialNumber: String,
    val ip: String,
    val isOnline: Boolean
)
