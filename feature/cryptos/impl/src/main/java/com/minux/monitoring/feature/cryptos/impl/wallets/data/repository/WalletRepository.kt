package com.minux.monitoring.feature.cryptos.impl.wallets.data.repository

import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletChangeDto
import com.minux.monitoring.feature.cryptos.api.model.WalletDto
import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletInputDto
import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletRemoveDto
import kotlinx.coroutines.flow.Flow

internal interface WalletRepository {
    fun getAllWallets(): Flow<Result<List<WalletDto>>>

    fun addWallet(walletInput: WalletInputDto): Flow<Result<WalletDto>>

    fun changeWallet(walletChange: WalletChangeDto): Flow<Result<WalletDto>>

    fun removeWallet(walletRemove: WalletRemoveDto): Flow<Result<Unit>>
}