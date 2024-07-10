package com.minux.monitoring.feature.devices.model.gpu

class GPUMiningInfoModel(
    val coreClock: Int,
    val coreClockUnit: String,
    val memoryClock: Int,
    val memoryClockUnit: String,
    val criticalTemperature: Int,
    val powerLimit: Int,
    val powerLimitUnit: String,
    val flightSheetName: String,
    val minerName: String
)