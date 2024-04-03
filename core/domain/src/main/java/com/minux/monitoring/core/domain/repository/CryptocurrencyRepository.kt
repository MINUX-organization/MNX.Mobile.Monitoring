package com.minux.monitoring.core.domain.repository

import com.minux.monitoring.core.domain.model.cryptocurrency.Cryptocurrency
import com.minux.monitoring.core.domain.model.cryptocurrency.CryptocurrencyInputParam
import com.minux.monitoring.core.domain.model.cryptocurrency.CryptocurrencyRemoveParam
import kotlinx.coroutines.flow.Flow

interface CryptocurrencyRepository {
    fun getAvailableAlgorithms(): Flow<Result<List<String>>>

    fun getAllCryptocurrencies(): Flow<Result<List<Cryptocurrency>>>

    fun addCryptocurrency(param: CryptocurrencyInputParam): Flow<Result<Cryptocurrency>>

    fun removeCryptocurrency(param: CryptocurrencyRemoveParam): Flow<Result<Unit>>
}