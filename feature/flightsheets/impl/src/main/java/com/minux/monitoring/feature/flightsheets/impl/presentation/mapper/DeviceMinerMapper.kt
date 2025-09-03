package com.minux.monitoring.feature.flightsheets.impl.presentation.mapper

import com.minux.monitoring.feature.flightsheets.impl.data.model.device.miner.DeviceMinerDto
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel

internal fun DeviceMinerDto.toDeviceMinerModel(): DeviceMinerModel {
    return DeviceMinerModel(
        id = id,
        name = name,
        version = version,
        supportedDevices = supportedDevices.map { it.toSupportedDeviceModel() },
        supportedMiningModes = miningMode.toMiningModes()
    )
}