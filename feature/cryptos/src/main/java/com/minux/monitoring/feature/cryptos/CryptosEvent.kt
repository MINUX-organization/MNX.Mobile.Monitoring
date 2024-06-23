package com.minux.monitoring.feature.cryptos

import com.minux.monitoring.core.data.model.cryptocurrency.CryptocurrencyInputParam
import com.minux.monitoring.core.data.model.cryptocurrency.CryptocurrencyRemoveParam

sealed interface CryptosEvent {
    data class AddCryptocurrency(
        val cryptocurrencyInputParam: CryptocurrencyInputParam
    ) : CryptosEvent

    data class RemoveCryptocurrency(
        val cryptocurrencyRemoveParam: CryptocurrencyRemoveParam
    ) : CryptosEvent
}