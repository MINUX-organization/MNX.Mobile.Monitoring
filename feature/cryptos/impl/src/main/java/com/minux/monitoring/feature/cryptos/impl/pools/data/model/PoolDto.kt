package com.minux.monitoring.feature.cryptos.impl.pools.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class PoolDto(
    val id: String,
    val domain: String,
    val port: Int,
    val cryptocurrency: String
)
