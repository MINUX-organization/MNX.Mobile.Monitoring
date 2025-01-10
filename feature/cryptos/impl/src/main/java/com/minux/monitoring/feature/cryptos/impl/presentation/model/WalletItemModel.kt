package com.minux.monitoring.feature.cryptos.impl.presentation.model

import com.minux.monitoring.feature.cryptos.impl.data.model.wallet.WalletDto

internal class WalletItemModel(
    val id: String,
    val name: String,
    val address: String,
    val cryptocurrency: String
)

internal fun WalletDto.toWalletItemModel(): WalletItemModel {
    return WalletItemModel(
        id = id,
        name = name,
        address = address,
        cryptocurrency = cryptocurrency
    )
}