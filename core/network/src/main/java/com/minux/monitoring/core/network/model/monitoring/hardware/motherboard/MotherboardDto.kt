package com.minux.monitoring.core.network.model.monitoring.hardware.motherboard

data class MotherboardDto(
    val name: String,
    val serialNumber: String,
    val sataPortsCount: Int,
    val ramPortsCount: Int,
    val pcix4Count: Int,
    val pcix16Count: Int,
    val ramsState: List<Boolean>,
    val satasState: List<String>
)
