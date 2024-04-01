package com.minux.monitoring.feature.cryptos

import com.minux.monitoring.core.domain.model.cryptocurrency.CryptocurrencyInputParam
import com.minux.monitoring.core.domain.model.cryptocurrency.CryptocurrencyRemoveParam

sealed interface CryptosEvent {
    data class AddCryptocurrency(
        val cryptocurrencyInputParam: CryptocurrencyInputParam
    ) : CryptosEvent

    data class RemoveCryptocurrency(
        val cryptocurrencyRemoveParam: CryptocurrencyRemoveParam
    ) : CryptosEvent
}