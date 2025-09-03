package com.minux.monitoring.feature.flightsheets.impl.data.model.mining

import kotlinx.serialization.Serializable

@Serializable
internal class MiningWalletDto(
    val id: String,
    val name: String?,
    val address: String?,
    val cryptocurrencyId: String,
    val cryptocurrency: String?
)