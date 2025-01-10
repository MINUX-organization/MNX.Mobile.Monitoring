package com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.model

import com.minux.monitoring.feature.cryptos.impl.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.PoolItemModel

internal data class PoolsUiState(
    val coins: List<CryptocurrencyItemModel> = emptyList(),
    val pools: List<PoolItemModel> = emptyList()
)