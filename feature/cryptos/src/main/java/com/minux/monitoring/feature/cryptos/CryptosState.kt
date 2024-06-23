package com.minux.monitoring.feature.cryptos

import com.minux.monitoring.core.data.model.cryptocurrency.Cryptocurrency

data class CryptosState(
    val cryptoAlgorithms: List<String> = emptyList(),
    val cryptos: List<Cryptocurrency> = emptyList()
)
