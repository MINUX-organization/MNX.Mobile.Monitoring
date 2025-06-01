package com.minux.monitoring.feature.cryptos.api.model

import kotlinx.serialization.Serializable

@Serializable
class WalletDto(
    val id: String,
    val name: String?,
    val address: String?,
    val cryptocurrencyId: String,
    val cryptocurrency: String?
)
