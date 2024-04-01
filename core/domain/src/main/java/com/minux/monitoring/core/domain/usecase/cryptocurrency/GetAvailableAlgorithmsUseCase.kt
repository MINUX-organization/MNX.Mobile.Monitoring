package com.minux.monitoring.core.domain.usecase.cryptocurrency

import com.minux.monitoring.core.domain.repository.CryptocurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetAvailableAlgorithmsUseCase(private val cryptocurrencyRepository: CryptocurrencyRepository) {
    operator fun invoke(): Flow<Result<List<String>>> {
        return cryptocurrencyRepository.getAvailableAlgorithms()
    }
}