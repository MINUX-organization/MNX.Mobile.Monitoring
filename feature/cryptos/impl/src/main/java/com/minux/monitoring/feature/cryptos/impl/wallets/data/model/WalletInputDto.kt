package com.minux.monitoring.feature.cryptos.impl.wallets.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class WalletInputDto(
    val name: String,
    val address: String,
    val cryptocurrencyId: String
)
