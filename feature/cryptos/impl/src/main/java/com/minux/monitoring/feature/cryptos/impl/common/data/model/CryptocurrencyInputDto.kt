package com.minux.monitoring.feature.cryptos.impl.common.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class CryptocurrencyInputDto(
    val shortName: String,
    val fullName: String,
    val algorithmId: String
)
