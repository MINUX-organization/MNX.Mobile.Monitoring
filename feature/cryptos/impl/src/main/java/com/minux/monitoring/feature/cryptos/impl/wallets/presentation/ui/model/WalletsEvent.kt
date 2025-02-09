package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel

internal sealed interface WalletsEvent {
    class NameChanged(val name: String) : WalletsEvent

    class AddressChanged(val address: String) : WalletsEvent

    class CoinChanged(val coin: CryptocurrencyItemModel?) : WalletsEvent

    class SelectWallet(val wallet: WalletItemModel) : WalletsEvent

    class RemoveWallet(val id: String) : WalletsEvent

    data object AddWallet : WalletsEvent

    data object ChangeWallet : WalletsEvent
}