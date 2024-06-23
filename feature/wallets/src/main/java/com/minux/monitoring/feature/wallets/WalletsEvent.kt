package com.minux.monitoring.feature.wallets

import com.minux.monitoring.core.data.model.wallet.WalletChangeParam
import com.minux.monitoring.core.data.model.wallet.WalletInputParam
import com.minux.monitoring.core.data.model.wallet.WalletRemoveParam

sealed interface WalletsEvent {
    data class AddWallet(val walletInputParam: WalletInputParam) : WalletsEvent

    data class ChangeWallet(val walletChangeParam: WalletChangeParam) : WalletsEvent

    data class RemoveWallet(val walletRemoveParam: WalletRemoveParam) : WalletsEvent
}