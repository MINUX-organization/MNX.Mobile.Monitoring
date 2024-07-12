package com.minux.monitoring.core.data.model.device

import com.minux.monitoring.core.data.model.metrics.ValueUnit

data class DeviceInformation(
    val id: String,
    val index: Int,
    val bus: Int,
    val name: String,
    val rigName: String,
    val flightSheetName: String,
    val minerName: String,
    val coreClock: ValueUnit,
    val memoryClock: ValueUnit,
    val criticalTemperature: Int,
    val powerLimit: ValueUnit,
    val manufacturer: String,
    val driver: String,
    val cuda: String,
    val vendor: String,
    val memorySize: ValueUnit,
    val memoryVendor: String,
    val memoryType: String,
    val firmwareVersion: String
)