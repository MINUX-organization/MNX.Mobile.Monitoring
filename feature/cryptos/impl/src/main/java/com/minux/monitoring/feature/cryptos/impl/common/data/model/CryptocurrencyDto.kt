package com.minux.monitoring.feature.cryptos.impl.common.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class CryptocurrencyDto(
    val id: String,
    val shortName: String,
    val fullName: String,
    val algorithm: AlgorithmDto
)
