package com.minux.monitoring.feature.cryptos.impl.pools.presentation.mapper

import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolDto
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel

internal fun PoolDto.toPoolItemModel(): PoolItemModel {
    return PoolItemModel(
        id = id,
        domain = domain,
        port = port,
        cryptocurrency = cryptocurrency
    )
}