package com.minux.monitoring.core.domain.usecase.wallet

import com.minux.monitoring.core.domain.model.wallet.Wallet
import com.minux.monitoring.core.domain.model.wallet.WalletInputParam
import com.minux.monitoring.core.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow

class AddWalletUseCase(private val walletRepository: WalletRepository) {
    operator fun invoke(walletInputParam: WalletInputParam): Flow<Result<Wallet>> {
        return walletRepository.addWallet(param = walletInputParam)
    }
}