package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.mapper

import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletInputDto
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletInputModel

internal fun WalletInputModel.toWalletInputDto(): WalletInputDto {
    return WalletInputDto(
        name = name,
        address = address,
        cryptocurrencyId = cryptocurrency!!.id
    )
}