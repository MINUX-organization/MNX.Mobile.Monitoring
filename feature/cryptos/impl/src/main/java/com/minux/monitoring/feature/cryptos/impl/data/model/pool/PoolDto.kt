package com.minux.monitoring.feature.cryptos.impl.data.model.pool

data class PoolDto(
    val id: String,
    val domain: String,
    val port: Int,
    val cryptocurrency: String
)
