package com.minux.monitoring.feature.flightsheets.impl.data.model.mining

import kotlinx.serialization.Serializable

@Serializable
internal class MiningCoinConfigInputDto(
    val poolId: String,
    val poolPassword: String?,
    val walletId: String
)