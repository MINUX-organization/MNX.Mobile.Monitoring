package com.minux.monitoring.feature.wallets

import com.minux.monitoring.core.data.model.wallet.Wallet

data class WalletsState(
    val coins: List<String> = emptyList(),
    val wallets: List<Wallet> = emptyList()
)
