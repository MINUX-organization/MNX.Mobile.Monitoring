package com.minux.monitoring.feature.cryptos.impl.wallets.data

import com.minux.monitoring.feature.cryptos.impl.wallets.data.datasource.WalletApiService
import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletChangeDto
import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletDto
import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletInputDto
import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletRemoveDto
import kotlinx.coroutines.flow.Flow

internal class WalletRepositoryImpl(private val walletApiService: WalletApiService) :
    WalletRepository {

    override fun getAllWallets(): Flow<Result<List<WalletDto>>> {
        return walletApiService.getAllWallets()
    }

    override fun addWallet(walletInput: WalletInputDto): Flow<Result<WalletDto>> {
        return walletApiService.addWallet(input = walletInput)
    }

    override fun changeWallet(walletChange: WalletChangeDto): Flow<Result<WalletDto>> {
        return walletApiService.changeWallet(
            id = walletChange.id,
            input = walletChange.wallet
        )
    }

    override fun removeWallet(walletRemove: WalletRemoveDto): Flow<Result<Unit>> {
        return walletApiService.removeWallet(id = walletRemove.id)
    }
}