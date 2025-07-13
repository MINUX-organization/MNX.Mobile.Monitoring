package com.minux.monitoring.feature.flightsheets.impl.data.model.mining

import kotlinx.serialization.Serializable

@Serializable
internal class MiningCoinConfigDto(
    val pool: MiningPoolDto?,
    val poolPassword: String? = null,
    val wallet: MiningWalletDto?
)