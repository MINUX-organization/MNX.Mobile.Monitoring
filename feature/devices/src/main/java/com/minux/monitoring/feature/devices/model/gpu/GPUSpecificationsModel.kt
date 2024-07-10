package com.minux.monitoring.feature.devices.model.gpu

class GPUSpecificationsModel(
    val manufacturer: String,
    val vendor: String,
    val memorySize: Int,
    val memorySizeUnit: String,
    val memoryVendor: String,
    val memoryType: String,
)