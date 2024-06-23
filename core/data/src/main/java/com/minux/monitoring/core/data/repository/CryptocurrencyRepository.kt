package com.minux.monitoring.core.data.repository

import com.minux.monitoring.core.data.model.cryptocurrency.Cryptocurrency
import com.minux.monitoring.core.data.model.cryptocurrency.CryptocurrencyInputParam
import com.minux.monitoring.core.data.model.cryptocurrency.CryptocurrencyRemoveParam
import kotlinx.coroutines.flow.Flow

interface CryptocurrencyRepository {
    fun getAvailableAlgorithms(): Flow<Result<List<String>>>

    fun getAllCryptocurrencies(): Flow<Result<List<Cryptocurrency>>>

    fun addCryptocurrency(param: CryptocurrencyInputParam): Flow<Result<Cryptocurrency>>

    fun removeCryptocurrency(param: CryptocurrencyRemoveParam): Flow<Result<Unit>>
}