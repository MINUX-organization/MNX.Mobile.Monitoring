package com.minux.monitoring.feature.cryptos.impl.pools.presentation.model

internal class PoolItemModel(
    val id: String,
    val domain: String?,
    val port: Int,
    val cryptocurrencyId: String,
    val cryptocurrency: String?,
)