package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.mapper

import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletDto
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel

internal fun WalletDto.toWalletItemModel(): WalletItemModel {
    return WalletItemModel(
        id = id,
        name = name,
        address = address,
        cryptocurrency = cryptocurrency
    )
}