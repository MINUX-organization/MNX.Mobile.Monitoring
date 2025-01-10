package com.minux.monitoring.feature.cryptos.impl.presentation.model

import com.minux.monitoring.feature.cryptos.impl.data.model.pool.PoolDto

internal class PoolItemModel(
    val id: String,
    val domain: String,
    val port: Int,
    val cryptocurrency: String
)

internal fun PoolDto.toPoolItemModel(): PoolItemModel {
    return PoolItemModel(
        id = id,
        domain = domain,
        port = port,
        cryptocurrency = cryptocurrency
    )
}