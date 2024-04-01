package com.minux.monitoring.core.domain.usecase.cryptocurrency

import com.minux.monitoring.core.domain.model.cryptocurrency.CryptocurrencyInputParam
import com.minux.monitoring.core.domain.repository.CryptocurrencyRepository

class AddCryptocurrencyUseCase(private val cryptocurrencyRepository: CryptocurrencyRepository) {
    operator fun invoke(cryptocurrencyInputParam: CryptocurrencyInputParam) =
        cryptocurrencyRepository.addCryptocurrency(param = cryptocurrencyInputParam)
}