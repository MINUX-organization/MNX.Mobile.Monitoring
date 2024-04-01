package com.minux.monitoring.core.domain.usecase.wallet

import com.minux.monitoring.core.domain.model.wallet.Wallet
import com.minux.monitoring.core.domain.model.wallet.WalletChangeParam
import com.minux.monitoring.core.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow

class ChangeWalletUseCase(private val walletRepository: WalletRepository) {
    operator fun invoke(walletChangeParam: WalletChangeParam): Flow<Result<Wallet>> {
        return walletRepository.changeWallet(param = walletChangeParam)
    }
}