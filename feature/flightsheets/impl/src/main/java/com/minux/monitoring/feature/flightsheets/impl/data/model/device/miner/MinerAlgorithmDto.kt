package com.minux.monitoring.feature.flightsheets.impl.data.model.device.miner

import kotlinx.serialization.Serializable

@Serializable
internal class MinerAlgorithmDto(
    val id: String,
    val name: String? = null
)