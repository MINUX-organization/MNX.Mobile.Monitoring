package com.minux.monitoring.feature.cryptos.impl.common.data

import com.minux.monitoring.feature.cryptos.impl.common.data.datasource.AlgorithmApiService
import com.minux.monitoring.feature.cryptos.impl.common.data.datasource.CryptocurrencyApiService
import com.minux.monitoring.feature.cryptos.impl.common.data.model.AlgorithmDto
import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyDto
import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyInputDto
import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyRemoveDto
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