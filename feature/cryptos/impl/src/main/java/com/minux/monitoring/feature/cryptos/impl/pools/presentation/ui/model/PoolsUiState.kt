package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolInputModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.SelectedPoolModel

internal data class PoolsUiState(
    val coins: List<CryptocurrencyItemModel> = emptyList(),
    val pools: List<PoolItemModel> = emptyList(),
    val poolInput: PoolInputModel = PoolInputModel(),
    val selectedPool: SelectedPoolModel? = null
)