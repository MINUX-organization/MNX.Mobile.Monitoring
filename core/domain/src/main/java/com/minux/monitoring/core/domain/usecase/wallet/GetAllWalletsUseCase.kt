package com.minux.monitoring.core.domain.usecase.wallet

import com.minux.monitoring.core.domain.model.wallet.Wallet
import com.minux.monitoring.core.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow

class GetAllWalletsUseCase(private val walletRepository: WalletRepository) {
    operator fun invoke(): Flow<Result<List<Wallet>>> {
        return walletRepository.getAllWallets()
    }
}