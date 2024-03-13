package com.minux.monitoring.core.network.model.management.pool

data class PoolInputDto(
    val domain: String,
    val port: Int,
    val cryptocurrencyId: String
)
