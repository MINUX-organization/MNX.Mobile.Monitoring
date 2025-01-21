package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyInputModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel

internal data class CryptosUiState(
    val cryptoAlgorithms: List<AlgorithmItemModel> = emptyList(),
    val cryptos: List<CryptocurrencyItemModel> = emptyList(),
    val cryptocurrencyInput: CryptocurrencyInputModel = CryptocurrencyInputModel()
)
