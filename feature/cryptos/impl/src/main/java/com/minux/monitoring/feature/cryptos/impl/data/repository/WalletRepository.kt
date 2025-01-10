package com.minux.monitoring.feature.cryptos.impl.data.repository

import com.minux.monitoring.feature.cryptos.impl.data.model.wallet.WalletChangeDto
import com.minux.monitoring.feature.cryptos.impl.data.model.wallet.WalletDto
import com.minux.monitoring.feature.cryptos.impl.data.model.wallet.WalletInputDto
import com.minux.monitoring.feature.cryptos.impl.data.model.wallet.WalletRemoveDto
import kotlinx.coroutines.flow.Flow

internal interface WalletRepository {
    fun getAllWallets(): Flow<Result<List<WalletDto>>>

    fun addWallet(walletInput: WalletInputDto): Flow<Result<WalletDto>>

    fun changeWallet(walletChange: WalletChangeDto): Flow<Result<WalletDto>>

    fun removeWallet(walletRemove: WalletRemoveDto): Flow<Result<Unit>>
}