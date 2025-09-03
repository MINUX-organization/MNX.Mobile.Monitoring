package com.minux.monitoring.feature.cryptos.impl.pools.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class PoolInputDto(
    val tls: Boolean,
    val domain: String?,
    val port: Int,
    val cryptocurrencyId: String
)
