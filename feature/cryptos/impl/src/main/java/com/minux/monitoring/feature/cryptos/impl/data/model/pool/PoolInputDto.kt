package com.minux.monitoring.feature.cryptos.impl.data.model.pool

data class PoolInputDto(
    val domain: String,
    val port: Int,
    val cryptocurrencyId: String
)
