package com.minux.monitoring.core.data.repository

import com.minux.monitoring.core.data.model.wallet.Wallet
import com.minux.monitoring.core.data.model.wallet.WalletChangeParam
import com.minux.monitoring.core.data.model.wallet.WalletInputParam
import com.minux.monitoring.core.data.model.wallet.WalletRemoveParam
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    fun getAllWallets(): Flow<Result<List<Wallet>>>

    fun addWallet(param: WalletInputParam): Flow<Result<Wallet>>

    fun changeWallet(param: WalletChangeParam): Flow<Result<Wallet>>

    fun removeWallet(param: WalletRemoveParam): Flow<Result<Unit>>
}