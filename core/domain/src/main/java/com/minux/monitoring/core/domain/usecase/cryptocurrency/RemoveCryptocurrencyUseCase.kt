package com.minux.monitoring.core.domain.usecase.cryptocurrency

import com.minux.monitoring.core.domain.model.cryptocurrency.CryptocurrencyRemoveParam
import com.minux.monitoring.core.domain.repository.CryptocurrencyRepository

class RemoveCryptocurrencyUseCase(private val cryptocurrencyRepository: CryptocurrencyRepository) {
    operator fun invoke(cryptocurrencyRemoveParam: CryptocurrencyRemoveParam) =
        cryptocurrencyRepository.removeCryptocurrency(param = cryptocurrencyRemoveParam)
}