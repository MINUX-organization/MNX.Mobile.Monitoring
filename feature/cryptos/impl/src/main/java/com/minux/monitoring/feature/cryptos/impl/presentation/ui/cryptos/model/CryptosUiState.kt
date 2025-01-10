package com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.model

import com.minux.monitoring.feature.cryptos.impl.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.CryptocurrencyItemModel

internal data class CryptosUiState(
    val cryptoAlgorithms: List<AlgorithmItemModel> = emptyList(),
    val cryptos: List<CryptocurrencyItemModel> = emptyList()
)
