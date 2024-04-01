package com.minux.monitoring.core.domain.repository

import com.minux.monitoring.core.domain.model.wallet.Wallet
import com.minux.monitoring.core.domain.model.wallet.WalletChangeParam
import com.minux.monitoring.core.domain.model.wallet.WalletInputParam
import com.minux.monitoring.core.domain.model.wallet.WalletRemoveParam
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    fun getAllWallets(): Flow<Result<List<Wallet>>>

    fun addWallet(param: WalletInputParam): Flow<Result<Wallet>>

    fun changeWallet(param: WalletChangeParam): Flow<Result<Wallet>>

    fun removeWallet(param: WalletRemoveParam): Flow<Result<Unit>>
}