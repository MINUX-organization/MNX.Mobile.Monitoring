package com.minux.monitoring.core.network.model.monitoring.devices

import com.minux.monitoring.core.network.model.monitoring.common.ValueUnitDto

data class DevicesInformationDto(
    val id: String,
    val index: Int,
    val bus: Int,
    val name: String,
    val rigName: String,
    val flightSheetName: String,
    val minerName: String,
    val coreClock: ValueUnitDto,
    val memoryClock: ValueUnitDto,
    val criticalTemperature: Int,
    val powerLimit: ValueUnitDto,
    val manufacturer: String,
    val driver: String,
    val cuda: String,
    val vendor: String,
    val memorySize: ValueUnitDto,
    val memoryVendor: String,
    val memoryType: String,
    val biosVersion: String // rename to firmwareVersion
)
