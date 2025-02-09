package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.SelectedWalletModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletInputModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel

internal data class WalletsUiState(
    val coins: List<CryptocurrencyItemModel> = emptyList(),
    val wallets: List<WalletItemModel> = emptyList(),
    val walletInput: WalletInputModel = WalletInputModel(),
    val selectedWallet: SelectedWalletModel? = null
)
