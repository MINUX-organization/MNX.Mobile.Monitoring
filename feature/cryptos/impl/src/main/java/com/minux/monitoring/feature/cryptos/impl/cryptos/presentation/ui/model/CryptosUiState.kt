package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyInputModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel

internal data class CryptosUiState(
    val cryptoAlgorithmsIsLoading: Boolean = true,
    val cryptoAlgorithms: List<AlgorithmItemModel>? = null,
    val cryptosIsLoading: Boolean = true,
    val cryptos: List<CryptocurrencyItemModel>? = null,
    val filteredCryptos: List<CryptocurrencyItemModel>? = null,
    val searchQuery: String = "",
    val cryptocurrencyInput: CryptocurrencyInputModel = CryptocurrencyInputModel()
)