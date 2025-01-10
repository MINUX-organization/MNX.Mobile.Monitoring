package com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.model

import com.minux.monitoring.feature.cryptos.impl.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.WalletItemModel

internal data class WalletsUiState(
    val coins: List<CryptocurrencyItemModel> = emptyList(),
    val wallets: List<WalletItemModel> = emptyList()
)
