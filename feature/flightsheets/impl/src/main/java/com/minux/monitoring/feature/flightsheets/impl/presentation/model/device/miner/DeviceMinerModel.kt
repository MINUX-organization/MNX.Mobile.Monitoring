package com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner

internal class DeviceMinerModel(
    val id: String,
    val name: String?,
    val version: String?,
    val supportedDevices: List<SupportedDeviceModel?>,
    val supportedMiningModes: List<MiningModeModel>
) {
    override fun toString(): String = "$name - $version"
}