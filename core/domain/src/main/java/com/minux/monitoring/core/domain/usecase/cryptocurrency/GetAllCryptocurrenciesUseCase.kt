package com.minux.monitoring.core.domain.usecase.cryptocurrency

import com.minux.monitoring.core.domain.model.cryptocurrency.Cryptocurrency
import com.minux.monitoring.core.domain.repository.CryptocurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCryptocurrenciesUseCase @Inject constructor(private val cryptocurrencyRepository: CryptocurrencyRepository) {
    operator fun invoke(): Flow<Result<List<Cryptocurrency>>> {
        return cryptocurrencyRepository.getAllCryptocurrencies()
    }
}