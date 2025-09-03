package com.minux.monitoring.feature.cryptos.api.model

import kotlinx.serialization.Serializable

@Serializable
class PoolDto(
    val id: String,
    val userId: String? = null,
    val tls: Boolean,
    val domain: String?,
    val port: Int,
    val cryptocurrencyId: String,
    val cryptocurrency: String?
)