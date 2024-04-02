package com.minux.monitoring.core.domain.usecase.wallet

import com.minux.monitoring.core.domain.model.wallet.WalletRemoveParam
import com.minux.monitoring.core.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow

class RemoveWalletUseCase(private val walletRepository: WalletRepository) {
    operator fun invoke(walletRemoveParam: WalletRemoveParam): Flow<Result<Unit>> {
        return walletRepository.removeWallet(param = walletRemoveParam)
    }
}