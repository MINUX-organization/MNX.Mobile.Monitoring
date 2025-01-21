package com.minux.monitoring.feature.cryptos.impl.common.data

import com.minux.monitoring.feature.cryptos.impl.common.data.model.AlgorithmDto
import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyDto
import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyInputDto
import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyRemoveDto
import kotlinx.coroutines.flow.Flow

internal interface CryptocurrencyRepository {
    fun getAvailableAlgorithms(): Flow<Result<List<AlgorithmDto>>>

    fun getAllCryptocurrencies(): Flow<Result<List<CryptocurrencyDto>>>

    fun addCryptocurrency(
        cryptocurrencyInput: CryptocurrencyInputDto
    ): Flow<Result<CryptocurrencyDto>>

    fun removeCryptocurrency(cryptocurrencyRemove: CryptocurrencyRemoveDto): Flow<Result<Unit>>
}