package com.minux.monitoring.feature.cryptos.api

import com.minux.monitoring.feature.cryptos.api.model.PoolDto
import com.minux.monitoring.feature.cryptos.api.model.WalletDto
import kotlinx.coroutines.flow.Flow

interface CryptosProvider {
    fun getPools(): Flow<Result<List<PoolDto>>>

    fun getWallets(): Flow<Result<List<WalletDto>>>
}