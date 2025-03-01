package com.minux.monitoring.feature.cryptos.impl.wallets.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class WalletDto(
    val id: String,
    val name: String,
    val address: String,
    val cryptocurrency: String
)
