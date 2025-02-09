package com.minux.monitoring.feature.cryptos.impl.pools.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class PoolChangeDto(
    val id: String,
    val pool: PoolInputDto
)
