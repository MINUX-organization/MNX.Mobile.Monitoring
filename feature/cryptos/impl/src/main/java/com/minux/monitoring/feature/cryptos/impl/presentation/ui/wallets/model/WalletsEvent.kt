package com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.model

internal sealed interface WalletsEvent {
    class AddWallet(val wallet: com.minux.monitoring.feature.cryptos.impl.presentation.model.WalletInputModel) :
        WalletsEvent

    class ChangeWallet(val id: String, val wallet: com.minux.monitoring.feature.cryptos.impl.presentation.model.WalletInputModel) :
        WalletsEvent

    class RemoveWallet(val id: String) : WalletsEvent
}