package com.minux.monitoring.feature.cryptos.impl.data.repository

import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.AlgorithmDto
import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.CryptocurrencyDto
import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.CryptocurrencyInputDto
import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.CryptocurrencyRemoveDto
import kotlinx.coroutines.flow.Flow

internal interface CryptocurrencyRepository {
    fun getAvailableAlgorithms(): Flow<Result<List<AlgorithmDto>>>

    fun getAllCryptocurrencies(): Flow<Result<List<CryptocurrencyDto>>>

    fun addCryptocurrency(
        cryptocurrencyInput: CryptocurrencyInputDto
    ): Flow<Result<CryptocurrencyDto>>

    fun removeCryptocurrency(cryptocurrencyRemove: CryptocurrencyRemoveDto): Flow<Result<Unit>>
}