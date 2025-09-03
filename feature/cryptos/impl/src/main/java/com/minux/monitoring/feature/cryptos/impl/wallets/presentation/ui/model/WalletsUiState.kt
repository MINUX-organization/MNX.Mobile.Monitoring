package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletInputModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel

internal data class WalletsUiState(
    val coinsIsLoading: Boolean = true,
    val coins: List<CryptocurrencyItemModel>? = null,
    val walletsIsLoading: Boolean = true,
    val wallets: List<WalletItemModel>? = null,
    val filteredWallets: List<WalletItemModel>? = null,
    val searchQuery: String = "",
    val walletInput: WalletInputModel = WalletInputModel()
)