package com.minux.monitoring.core.network.model.monitoring.hardware.gpu

data class GpuInformationDto(
    val pciBusId: Int,
    val serialNumber: String,
    val memory: String,
    val driverVersion: String,
    val cudaVersion: String
)
