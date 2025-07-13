package com.minux.monitoring.feature.flightsheets.impl.data.model.device.miner

import kotlinx.serialization.Serializable

@Serializable
internal class DeviceMinerDto(
    val id: String,
    val name: String?,
    val version: String?,
    val supportedDevices: List<SupportedDeviceDto>,
    val supportedAlgorithms: List<MinerAlgorithmDto?>,
    val ownerId: String? = null,
    val installationUrl: String?,
    val poolTemplate: String? = null,
    val walletWorkerTemplate: String? = null,
    val miningMode: MiningModeDto
)