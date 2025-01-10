package com.minux.monitoring.feature.cryptos.impl.data.repository

import com.minux.monitoring.feature.cryptos.impl.data.datasource.AlgorithmApiService
import com.minux.monitoring.feature.cryptos.impl.data.datasource.CryptocurrencyApiService
import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.AlgorithmDto
import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.CryptocurrencyDto
import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.CryptocurrencyInputDto
import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.CryptocurrencyRemoveDto
import kotlinx.coroutines.flow.Flow

internal class CryptocurrencyRepositoryImpl(
    private val algorithmApiService: AlgorithmApiService,
    private val cryptocurrencyApiService: CryptocurrencyApiService
) : CryptocurrencyRepository {

    override fun getAvailableAlgorithms(): Flow<Result<List<AlgorithmDto>>> {
        return algorithmApiService.getAvailableAlgorithms()
    }

    override fun getAllCryptocurrencies(): Flow<Result<List<CryptocurrencyDto>>> {
        return cryptocurrencyApiService.getAllCryptocurrencies()
    }

    override fun addCryptocurrency(
        cryptocurrencyInput: CryptocurrencyInputDto
    ): Flow<Result<CryptocurrencyDto>> {
        return cryptocurrencyApiService.addCryptocurrency(
            cryptocurrencyInputDto = cryptocurrencyInput
        )
    }

    override fun removeCryptocurrency(
        cryptocurrencyRemove: CryptocurrencyRemoveDto
    ): Flow<Result<Unit>> {
        return cryptocurrencyApiService.removeCryptocurrency(id = cryptocurrencyRemove.id)
    }
}