package com.minux.monitoring.core.network.model.management.pool

data class PoolDto(
    val id: String,
    val domain: String,
    val port: Int,
    val cryptocurrency: String
)
