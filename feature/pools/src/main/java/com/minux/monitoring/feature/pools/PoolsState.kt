package com.minux.monitoring.feature.pools

import com.minux.monitoring.core.domain.model.pool.Pool

data class PoolsState(
    val coins: List<String> = emptyList(),
    val pools: List<Pool> = emptyList()
)
