package com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.model

import com.minux.monitoring.feature.cryptos.impl.presentation.model.CryptocurrencyInputModel

internal sealed interface CryptosEvent {
    class AddCryptocurrency(val cryptocurrency: CryptocurrencyInputModel) : CryptosEvent

    class RemoveCryptocurrency(val id: String) : CryptosEvent
}