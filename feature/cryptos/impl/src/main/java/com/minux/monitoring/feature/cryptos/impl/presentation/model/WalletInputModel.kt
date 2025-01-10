package com.minux.monitoring.feature.cryptos.impl.presentation.model

import com.minux.monitoring.feature.cryptos.impl.data.model.wallet.WalletInputDto

internal class WalletInputModel(
    val name: String,
    val address: String,
    val cryptocurrencyId: String
)

internal fun WalletInputModel.toWalletInputDto(): WalletInputDto {
    return WalletInputDto(
        name = name,
        address = address,
        cryptocurrencyId = cryptocurrencyId
    )
}