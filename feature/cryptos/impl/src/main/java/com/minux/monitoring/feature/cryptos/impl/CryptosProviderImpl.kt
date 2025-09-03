package com.minux.monitoring.feature.cryptos.impl

import com.minux.monitoring.feature.cryptos.api.CryptosProvider
import com.minux.monitoring.feature.cryptos.api.model.PoolDto
import com.minux.monitoring.feature.cryptos.api.model.WalletDto
import com.minux.monitoring.feature.cryptos.impl.pools.data.repository.PoolRepository
import com.minux.monitoring.feature.cryptos.impl.wallets.data.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class CryptosProviderImpl @Inject constructor(
    private val poolRepository: PoolRepository,
    private val walletRepository: WalletRepository
) : CryptosProvider {
    override fun getPools(): Flow<Result<List<PoolDto>>> {
        return poolRepository.getAllPools()
    }

    override fun getWallets(): Flow<Result<List<WalletDto>>> {
        return walletRepository.getAllWallets()
    }
}