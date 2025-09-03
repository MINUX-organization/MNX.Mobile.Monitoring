package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolInputModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel

internal data class PoolsUiState(
    val coinsIsLoading: Boolean = true,
    val coins: List<CryptocurrencyItemModel>? = null,
    val poolsIsLoading: Boolean = true,
    val pools: List<PoolItemModel>? = null,
    val filteredPools: List<PoolItemModel>? = null,
    val searchQuery: String = "",
    val poolInput: PoolInputModel = PoolInputModel()
)